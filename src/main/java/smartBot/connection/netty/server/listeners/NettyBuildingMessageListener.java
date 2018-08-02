package smartBot.connection.netty.server.listeners;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartBot.bean.*;
import smartBot.bussines.listeners.impl.SimpleProcessScopeAddedListener;
import smartBot.bussines.listeners.impl.SimpleProcessScopeCalculateListener;
import smartBot.bussines.listeners.impl.SimpleProcessScopeRemoveListener;
import smartBot.bussines.process.CurrencyRateProcess;
import smartBot.bussines.process.PriorityProcess;
import smartBot.bussines.process.SimpleScopeProcess;
import smartBot.bussines.service.CurrencyRatesService;
import smartBot.bussines.service.CurrencyService;
import smartBot.bussines.service.PriorityService;
import smartBot.bussines.service.ScopeService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.bussines.service.mapping.CurrencyRatesServiceMapper;
import smartBot.connection.netty.server.common.HostPort;
import smartBot.connection.netty.server.common.NettyMessage;
import smartBot.connection.netty.server.gateway.NettyBuildingMessageGateway;
import smartBot.connection.netty.server.messages.Message;
import smartBot.connection.netty.server.messages.MessageHeader;
import smartBot.connection.netty.server.messages.PingMessage;
import smartBot.defines.Constants;
import smartBot.defines.PriorityConstants;
import smartBot.defines.RequestsSocket;
import smartBot.defines.Strings;
import smartBot.utils.Json;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class NettyBuildingMessageListener implements NettyMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(NettyBuildingMessageListener.class);

    private NettyBuildingMessageGateway gateway;

    @Resource
    private CurrencyService currencyService;

    @Resource
    private CurrencyRatesServiceMapper currencyRatesServiceMapper;

    @Resource
    private CurrencyRatesService currencyRatesService;

    @Resource
    private ServerCache serverCache;

    @Resource
    private ScopeService scopeService;

    @Resource
    private SimpleScopeProcess simpleScopeProcess;

    @Resource
    private CurrencyRateProcess currencyRateProcess;

    @Resource
    private PriorityProcess priorityProcess;

    @Resource
    private PriorityService priorityService;

    @Resource
    private SimpleProcessScopeAddedListener simpleProcessScopeAddedListener;

    @Resource
    private SimpleProcessScopeRemoveListener simpleProcessScopeRemoveListener;

    @Resource
    private SimpleProcessScopeCalculateListener simpleProcessScopeCalculateListener;


    @Override
    public void messageReceived(NettyMessage<?> msg, HostPort hostPort) {

        Message message = (Message) msg;
        String strBody = message.getBody();

        if (logger.isDebugEnabled()) {

            logger.debug("Received Netty message size: {} from: {}  sending time {} ",
                    strBody != null ? strBody.length() : 0,
                    hostPort,
                    new DateTime().getMillis() -
                        (new DateTime(msg.getHeader().getHeaderValue(MessageHeader.TIMESTAMP)).getMillis()));
        }

        // TODO: ALL WORK SHOULD BE DONE HERE

        if (message instanceof PingMessage) {
            gateway.sendPongMessage(hostPort);
            return;
        }

        String strDestination = msg.getHeader().getHeaderValue(MessageHeader.DESTINATION);
        if (strDestination != null) {

            // Request on getting list of all currencies
            if (RequestsSocket.CURRENCIES_GET_ALL_LIST.equals(strDestination)) {
                List<Currency> currencyEntityList = currencyService.findAll();

                String allCurrencyShortNames = currencyEntityList.stream().map(p -> p.getShortName()).collect(Collectors.joining(","));

                gateway.sendMessage(
                        RequestsSocket.CURRENCIES_GET_ALL_LIST,
                        allCurrencyShortNames,
                        hostPort
                );
            }

            // Request for calculation zones (if needed)
            if (RequestsSocket.CURRENCIES_RATES_LIST.equals(strDestination)) {

                // received rates and start main calculate
                CurrencyRatesJson currencyRatesJson =
                        Json.readObjectFromString(strBody, CurrencyRatesJson.class);
                if (currencyRatesJson != null) {

                    if (simpleScopeProcess.getListeners().isEmpty()) {
                        simpleScopeProcess.registerScopeListener(simpleProcessScopeAddedListener);
                        simpleScopeProcess.registerScopeListener(simpleProcessScopeCalculateListener);
                        simpleScopeProcess.registerScopeListener(simpleProcessScopeRemoveListener);
                    }

                    CurrencyRates currentCurrencyRate = currencyRatesServiceMapper.mapJsonToBean(currencyRatesJson);

                    Integer currencyId = currentCurrencyRate.getCurrency().getId();
                    DateTime currencyRateTimeStamp = currentCurrencyRate.getTimestamp();

                    // Get Last CurrencyRates from DB to start with it (to avoid server sleep time)
                    CurrencyRates lastCurrencyRateFromHigh = currencyRatesService.findLastByCurrencyIdAndScopeType(currencyId, Scope.BUILD_FROM_HIGH);
                    CurrencyRates lastCurrencyRateFromLow = currencyRatesService.findLastByCurrencyIdAndScopeType(currencyId, Scope.BUILD_FROM_LOW);

                    // Check if we have a new high/low
                    boolean isNewCalculationFromHighNeeded = serverCache.isNewCalculationNeededOrSkip(lastCurrencyRateFromHigh, currentCurrencyRate,  Scope.BUILD_FROM_HIGH);
                    boolean isNewCalculationFromLowNeeded = serverCache.isNewCalculationNeededOrSkip(lastCurrencyRateFromLow, currentCurrencyRate, Scope.BUILD_FROM_LOW);

                    // Get last scope for currency
                    Scope scopeFromHigh = scopeService.findByCurrencyIdAndScopeType(currencyId, Scope.BUILD_FROM_HIGH);
                    Scope scopeFromLow = scopeService.findByCurrencyIdAndScopeType(currencyId, Scope.BUILD_FROM_LOW);

                    // Create a new scope if it does not exist
                    if (scopeFromHigh == null) {
                        scopeFromHigh = scopeService.create(currentCurrencyRate, Scope.BUILD_FROM_HIGH);
                        currentCurrencyRate.setScope(scopeFromHigh);
                        simpleScopeProcess.addScope(scopeFromHigh);
                    }
                    if (scopeFromLow == null) {
                        scopeFromLow = scopeService.create(currentCurrencyRate, Scope.BUILD_FROM_LOW);
                        currentCurrencyRate.setScope(scopeFromHigh);
                        simpleScopeProcess.addScope(scopeFromLow);
                    }

                    // Determine if zone was touched and mark it in DB
                    List<Scope> scopes = new ArrayList<>();
                    if (scopeFromHigh != null) { scopes.add(scopeFromHigh); }
                    if (scopeFromLow != null) { scopes.add(scopeFromLow); }

                    //----------------------------------
                    // We need to determine priority (without cron task) for each days that we had skipped
                    if (currencyRateTimeStamp.toDate().before(new Date())) {
                        DateTime dateTime1 = new DateTime(currencyRateTimeStamp).withTime(22, 59, 0, 0);
                        DateTime dateTime2 = new DateTime(currencyRateTimeStamp).withTime(23, 1, 0, 0);

                        Priority priorityLast = priorityService.findByCurrencyIdAndPrioritySubType(currencyId, PriorityConstants.LOCAL);

                        if (currencyRateTimeStamp.isAfter(dateTime1) && currencyRateTimeStamp.isBefore(dateTime2)
                                && (priorityLast == null || priorityLast.getStartDate().toDate().before(dateTime1.toDate()))) {

                            Priority priorityNew = priorityProcess.determinePriorityAndChange(scopes, currentCurrencyRate);

                            if (priorityNew != null && (priorityLast == null || priorityLast.getType().getType() != priorityNew.getType().getType())) {

                                // close last scope
                                Scope scopeLast = scopeFromHigh;
                                CurrencyRates currencyRateLast = lastCurrencyRateFromLow; // correct: we  need to get last inverse extremum
                                if (priorityNew.getType().getType() == PriorityConstants.SELL) {
                                    scopeLast = scopeFromLow;
                                    currencyRateLast = lastCurrencyRateFromHigh; // correct
                                }

                                DateTime moveCursorTo = currencyRateLast.getTimestamp();
                                serverCache.removeScopeFromCache(scopeLast);
                                serverCache.setCurrencyRatesToCache(currentCurrencyRate);

                                scopeService.delete(scopeLast);

                                // Create and save new priority to DB and cache
                                priorityNew = priorityService.create(priorityNew);

                                if (moveCursorTo != null) {
                                    // Send response to client
                                    gateway.sendMessage(
                                            RequestsSocket.PRIORITY_CHANGED,
                                            moveCursorTo.toString(DateTimeFormat.forPattern("yyyy.MM.dd HH:mm")) + Constants.COMMA +
                                                    priorityNew.getStartDate().toString(DateTimeFormat.forPattern("yyyy.MM.dd HH:mm")) + Constants.COMMA +
                                                    priorityNew.getType().getType(),
                                            hostPort
                                    );
                                    return;
                                }
                            }
                        }
                    }

                    // Determine type of scope
                    while (isNewCalculationFromLowNeeded || isNewCalculationFromHighNeeded) {
                        CurrencyRates currencyRates = new CurrencyRates();
                        currencyRatesService.merge(currentCurrencyRate, currencyRates);

                        Integer scopeType = (isNewCalculationFromHighNeeded ? Scope.BUILD_FROM_HIGH : (isNewCalculationFromLowNeeded ? Scope.BUILD_FROM_LOW : null));

                        if (scopeType != null) {
                            Scope scope = scopeFromHigh;
                            if (scopeType == Scope.BUILD_FROM_LOW) {
                                scope = scopeFromLow;
                            }

                            // reset flags to avoid additional loops
                            if (isNewCalculationFromHighNeeded) {
                                isNewCalculationFromHighNeeded = false;
                            } else if (isNewCalculationFromLowNeeded) {
                                isNewCalculationFromLowNeeded = false;
                            }

                            CurrencyRates lastCurrencyRate = currencyRatesService.findLastByScope(scope);
                            if (lastCurrencyRate == null) {
                                lastCurrencyRate = currencyRates;
                            } else {
                                currencyRatesService.merge(currencyRates, lastCurrencyRate);
                            }

                            if (lastCurrencyRate.getScope() == null || lastCurrencyRate.getScope().getType() != scope.getType()) {
                                lastCurrencyRate.setScope(scope);
                            }

                            lastCurrencyRate = currencyRatesService.save(lastCurrencyRate);

                            scope.setCurrencyRate(lastCurrencyRate);
                            serverCache.setScopeCache(scope);
                            serverCache.setCurrencyRatesToCache(lastCurrencyRate);

                            // Create/calculate scope and zones
                            currencyRateProcess.calculateZones(scope);
                        }
                    }

                    for (Scope scope : scopes) {
                        currencyRateProcess.touchZone(scope, currentCurrencyRate);
                    }
                }
            }
        }
    }

    private String prepareZonesMessageResponse(Scope scope) {
        StringBuilder sb = new StringBuilder();

        scope.getZones().stream().forEach(zone -> sb.append(scope.getType()).append(Strings.COMMA)
                .append(zone.getTimestamp()).append(Strings.COMMA)
                .append(zone.getPrice()).append(Strings.COMMA)
                .append(zone.getPriceCalc()).append(Strings.COMMA)
                .append(zone.getPriceCalcShift()));
        logger.info("> message size: " + sb.toString().length());
        return sb.toString();
    }

    public void setGateway(NettyBuildingMessageGateway gateway) {
        this.gateway = gateway;
    }

}
