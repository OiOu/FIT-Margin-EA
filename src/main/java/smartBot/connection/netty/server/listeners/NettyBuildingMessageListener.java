package smartBot.connection.netty.server.listeners;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import smartBot.bean.Currency;
import smartBot.bean.CurrencyRates;
import smartBot.bean.CurrencyRatesJson;
import smartBot.bean.Scope;
import smartBot.bussines.listeners.impl.SimpleProcessScopeAddedListener;
import smartBot.bussines.listeners.impl.SimpleProcessScopeCalculateListener;
import smartBot.bussines.listeners.impl.SimpleProcessScopeRemoveListener;
import smartBot.bussines.process.SimpleScopeProcess;
import smartBot.bussines.process.SimpleZoneProcess;
import smartBot.bussines.service.CurrencyService;
import smartBot.bussines.service.ScopeService;
import smartBot.bussines.service.ZoneService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.bussines.service.mapping.CurrencyRatesServiceMapper;
import smartBot.connection.netty.server.common.HostPort;
import smartBot.connection.netty.server.common.NettyMessage;
import smartBot.connection.netty.server.gateway.NettyBuildingMessageGateway;
import smartBot.connection.netty.server.messages.Message;
import smartBot.connection.netty.server.messages.MessageHeader;
import smartBot.connection.netty.server.messages.PingMessage;
import smartBot.defines.RequestsSocket;
import smartBot.defines.Strings;
import smartBot.utils.Json;

import java.util.List;
import java.util.stream.Collectors;

public class NettyBuildingMessageListener implements NettyMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(NettyBuildingMessageListener.class);

    private NettyBuildingMessageGateway gateway;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private ScopeService scopeService;

    @Autowired
    private CurrencyRatesServiceMapper currencyRatesServiceMapper;

    @Autowired
    private ServerCache serverCache;

    @Autowired
    private SimpleScopeProcess simpleScopeProcess;

    @Autowired
    private SimpleZoneProcess simpleZoneProcess;

    @Autowired
    private SimpleProcessScopeAddedListener simpleProcessScopeAddedListener;

    @Autowired
    private SimpleProcessScopeRemoveListener simpleProcessScopeRemoveListener;

    @Autowired
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

                // received rates and start main process
                CurrencyRatesJson currencyRatesJson =
                        Json.readObjectFromString(strBody, CurrencyRatesJson.class);
                if (currencyRatesJson != null) {
                    String messageToClient = "";
                    CurrencyRates currentCurrencyRate = currencyRatesServiceMapper.mapJsonToBean(currencyRatesJson);

                    // Check if we have a new high/low
                    boolean isNewCalculationFromHighNeeded = serverCache.isNewCalculationNeededOrSkip(currentCurrencyRate, Scope.BUILD_FROM_HIGH);
                    boolean isNewCalculationFromLowNeeded = serverCache.isNewCalculationNeededOrSkip(currentCurrencyRate, Scope.BUILD_FROM_LOW);

                    // Determine type of scope
                    while (isNewCalculationFromLowNeeded || isNewCalculationFromHighNeeded) {
                        Integer scopeType = (isNewCalculationFromHighNeeded ? Scope.BUILD_FROM_HIGH : (isNewCalculationFromLowNeeded ? Scope.BUILD_FROM_LOW : null));

                        if (scopeType != null) {
                            // reset flags to avoid additional loops
                            if (isNewCalculationFromHighNeeded) isNewCalculationFromHighNeeded = false;
                            else
                            if (isNewCalculationFromLowNeeded) isNewCalculationFromLowNeeded = false;

                            // Get last scope for currency. if not exists - create it
                            Scope scope = scopeService.findByCurrencyIdAndScopeTypeAndOnDate(currentCurrencyRate.getCurrency().getId(), scopeType, currentCurrencyRate.getTimestamp());

                            // Create a new scope if it's needed and calculate new zones
                            if (scope == null) {
                                scope = scopeService.create(currentCurrencyRate, scopeType);

                                if (simpleScopeProcess.getListeners().isEmpty()) {
                                    simpleScopeProcess.registerScopeListener(simpleProcessScopeAddedListener);
                                    simpleScopeProcess.registerScopeListener(simpleProcessScopeCalculateListener);
                                    simpleScopeProcess.registerScopeListener(simpleProcessScopeRemoveListener);
                                }

                                //serverCache.setScopeCache(scope);
                                simpleScopeProcess.addScope(scope);
                                // Calculate zones for each scope
                            }

                            simpleScopeProcess.process(scope, currentCurrencyRate);

                            messageToClient = prepareZonesMessageResponse(scope);

                            // Send response to client
                            if (StringUtils.isNotEmpty(messageToClient)) {
                                gateway.sendMessage(
                                        RequestsSocket.CURRENCIES_RATES_LIST,
                                        messageToClient,
                                        hostPort
                                );
                            }
                        }
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
