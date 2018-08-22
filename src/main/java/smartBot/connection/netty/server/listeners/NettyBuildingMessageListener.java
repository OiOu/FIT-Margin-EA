package smartBot.connection.netty.server.listeners;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartBot.bean.*;
import smartBot.bean.json.CurrencyRatesJson;
import smartBot.bean.json.OrderJSON;
import smartBot.bussines.listeners.impl.*;
import smartBot.bussines.process.CurrencyRateProcess;
import smartBot.bussines.process.OrderProcess;
import smartBot.bussines.process.PriorityProcess;
import smartBot.bussines.process.SimpleScopeProcess;
import smartBot.bussines.service.*;
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
    private MarginRatesService marginRateService;

    @Resource
    private OrderService orderService;

    @Resource
    private SimpleProcessScopeAddedListener simpleProcessScopeAddedListener;

    @Resource
    private SimpleProcessScopeRemoveListener simpleProcessScopeRemoveListener;

    @Resource
    private SimpleProcessScopeCalculateListener simpleProcessScopeCalculateListener;

    @Resource
    private OrderOpenListener orderOpenListener;

    @Resource
    private OrderModifyListener orderModifyListener;

    @Resource
    private OrderCloseListener orderCloseListener;

    @Resource
    private OrderProcess orderProcess;

    /*@PostConstruct
    public void postConstruct() {
        if (simpleScopeProcess.getListeners().isEmpty()) {
            simpleScopeProcess.registerScopeListener(simpleProcessScopeAddedListener);
            simpleScopeProcess.registerScopeListener(simpleProcessScopeCalculateListener);
            simpleScopeProcess.registerScopeListener(simpleProcessScopeRemoveListener);
        }

        if (orderProcess.getListeners().isEmpty()) {
            orderProcess.registerListener(orderOpenListener);
            orderProcess.registerListener(orderModifyListener);
            orderProcess.registerListener(orderCloseListener);
            orderOpenListener.setGateway(gateway);
            orderModifyListener.setGateway(gateway);
            orderCloseListener.setGateway(gateway);
        }
    }*/

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

            // Open order confirmation
            if (RequestsSocket.OPEN_ORDER.equals(strDestination)) {
                // received rates and start main calculate
                OrderJSON orderJson = Json.readObjectFromString(strBody, OrderJSON.class);
                if (orderJson != null) {
                    Order order = orderService.findByName(orderJson.getOrderName());
                    order.setTicket(orderJson.getTicket());
                    orderService.save(order);
                }
            }

            // Request for calculation zones (if needed)
            if (RequestsSocket.CURRENCY_RATE.equals(strDestination)) {

                // received rates and start main calculate
                CurrencyRatesJson currencyRatesJson =
                        Json.readObjectFromString(strBody, CurrencyRatesJson.class);
                if (currencyRatesJson != null) {

                    if (simpleScopeProcess.getListeners().isEmpty()) {
                        simpleScopeProcess.registerScopeListener(simpleProcessScopeAddedListener);
                        simpleScopeProcess.registerScopeListener(simpleProcessScopeCalculateListener);
                        simpleScopeProcess.registerScopeListener(simpleProcessScopeRemoveListener);
                    }

                    if (orderProcess.getListeners().isEmpty()) {
                        orderProcess.registerListener(orderOpenListener);
                        orderProcess.registerListener(orderModifyListener);
                        orderProcess.registerListener(orderCloseListener);
                        orderOpenListener.setGateway(gateway);
                        orderModifyListener.setGateway(gateway);
                        orderCloseListener.setGateway(gateway);
                    }

                    CurrencyRates currentCurrencyRate = currencyRatesServiceMapper.mapJsonToBean(currencyRatesJson);

                    Integer currencyId = currentCurrencyRate.getCurrency().getId();

                    // Get Last CurrencyRates from DB to start with it (to avoid server sleep time)
                    CurrencyRates lastCurrencyRateFromHigh = currencyRatesService.findLastByCurrencyIdAndScopeType(currencyId, Scope.BUILD_FROM_HIGH);
                    CurrencyRates lastCurrencyRateFromLow = currencyRatesService.findLastByCurrencyIdAndScopeType(currencyId, Scope.BUILD_FROM_LOW);

                    // Check if we have a new high/low
                    boolean isNewCalculationFromHighNeeded = serverCache.isNewCalculationNeededOrSkip(lastCurrencyRateFromHigh, currentCurrencyRate,  Scope.BUILD_FROM_HIGH);
                    boolean isNewCalculationFromLowNeeded = serverCache.isNewCalculationNeededOrSkip(lastCurrencyRateFromLow, currentCurrencyRate, Scope.BUILD_FROM_LOW);

                    // Try to get margin rate from cache and if not found get from DB and set force flag to recalculate all zones
                    MarginRates marginRate = marginRateService.findByCurrencyIdOnDate(currencyId, currentCurrencyRate.getTimestamp());

                    // Force calculation is needed during margin rate changes
                    if ( serverCache.isForceUpdateZoneNeeded() ) {
                        isNewCalculationFromHighNeeded = true;
                        isNewCalculationFromLowNeeded = true;
                    }

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

                    // We need to determine priority (without cron task) for each days that we skipped
                    // TODO move all priority process into CurrencyRateProcess
                    Priority priorityNew = priorityProcess.determinePriority(scopes, currentCurrencyRate);
                    if (priorityNew != null) {
                        serverCache.setCurrencyRatesToCache(currentCurrencyRate);

                        // close last scope
                        Scope scopeLast = scopeFromHigh;
                        CurrencyRates currencyRateLast = lastCurrencyRateFromLow; // correct: we  need to get last inverse extremum
                        if (priorityNew.getType().getType() == PriorityConstants.SELL) {
                            scopeLast = scopeFromLow;
                            currencyRateLast = lastCurrencyRateFromHigh; // correct
                        }

                        // Delete scope and related zones
                        scopeService.delete(scopeLast);

                        // Create and save new priority to DB and cache
                        priorityNew = priorityService.save(priorityNew);

                        DateTime moveCursorTo = currencyRateLast.getTimestamp();
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
                    String response = "";
                    // Determine type of scope and calculate zones
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

                            // Calculate only if high/low was updated
                            // for force flag only recalculation will be needed
                            CurrencyRates lastCurrencyRate = currencyRatesService.findLastByScope(scope);
                            if (lastCurrencyRate == null) {
                                lastCurrencyRate = currencyRates;
                            } else if (!serverCache.isForceUpdateZoneNeeded()) {
                                currencyRatesService.merge(currencyRates, lastCurrencyRate);
                            }

                            if (lastCurrencyRate.getScope() == null || lastCurrencyRate.getScope().getType() != scope.getType()) {
                                lastCurrencyRate.setScope(scope);
                            }

                            lastCurrencyRate = currencyRatesService.save(lastCurrencyRate);

                            scope.setCurrencyRate(lastCurrencyRate);
                            serverCache.setScopeToCache(scope);
                            serverCache.setCurrencyRatesToCache(lastCurrencyRate);

                            // Create/calculate scope and zones
                            currencyRateProcess.calculateZones(scope);
                        }
                    }

                    if (scopes != null && scopes.size() > 0) {
                        response = prepareZonesMessageResponse(scopes);
                    }

                    // Send response that CurrencyRate was processed
                    if (StringUtils.isNotEmpty(response)) {
                        gateway.sendMessage(
                                RequestsSocket.DRAW_ZONES,
                                response,
                                hostPort
                        );
                    } else {
                        gateway.sendMessage(
                                RequestsSocket.NEXT_RATE,
                                "next rate",
                                hostPort
                        );
                    }

                    // Reset force flag
                    if ( serverCache.isForceUpdateZoneNeeded() ) {
                        serverCache.setIsForceUpdateZoneNeeded(false);
                    }

                    // Process if zone was touched
                    for (Scope scope : scopes) {
                        currencyRateProcess.checkAndProcess(scope, currentCurrencyRate, hostPort);
                    }

                    // TODO Process open orders
                    for (Scope scope : scopes) {
                        currencyRateProcess.checkAndProcessOrders(scope, currentCurrencyRate, hostPort);
                    }
                }
            }
        }
    }

    private String prepareZonesMessageResponse(List<Scope> scopes) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy.MM.dd HH:mm");

        scopes.forEach(scope -> scope.getZones().stream().forEach(zone ->
                sb.append("zone_" + zone.getLevel().getK()+ "_" + scope.getType()).append(Strings.COMMA)
                    .append(dtf.print(zone.getTimestamp())).append(Strings.COMMA)
                    .append(zone.getPrice()).append(Strings.COMMA)
                    .append(zone.getPriceCalc()).append(Strings.COMMA)
                    .append(zone.getPriceCalcShift()).append(Strings.COMMA)
                    .append(zone.getLevel().getK()).append(Strings.COMMA)
                    .append(scope.getType())
                .append(Strings.VERTICAL_BAR_PIPE))
        );

        String res = sb.toString();
        if (res.endsWith(Strings.VERTICAL_BAR_PIPE)) {
           res = res.substring(0, res.length() -1);
        }

        logger.info("> message size: " + res.length());
        return res;
    }

    public void setGateway(NettyBuildingMessageGateway gateway) {
        this.gateway = gateway;
    }

}
