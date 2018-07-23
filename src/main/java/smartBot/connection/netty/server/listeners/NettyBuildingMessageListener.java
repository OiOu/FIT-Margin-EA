package smartBot.connection.netty.server.listeners;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import smartBot.bean.Currency;
import smartBot.bean.CurrencyRates;
import smartBot.bean.CurrencyRatesJson;
import smartBot.bean.Scope;
import smartBot.bussines.process.CurrencyRateProcess;
import smartBot.bussines.process.SimpleScopeProcess;
import smartBot.bussines.service.CurrencyRatesService;
import smartBot.bussines.service.CurrencyService;
import smartBot.bussines.service.ScopeService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.bussines.service.mapping.CurrencyRatesServiceMapper;
import smartBot.connection.netty.server.common.HostPort;
import smartBot.connection.netty.server.common.NettyMessage;
import smartBot.connection.netty.server.gateway.NettyBuildingMessageGateway;
import smartBot.connection.netty.server.messages.Message;
import smartBot.connection.netty.server.messages.MessageHeader;
import smartBot.connection.netty.server.messages.PingMessage;
import smartBot.defines.RequestsSocket;
import smartBot.utils.Json;

import java.util.List;
import java.util.stream.Collectors;

public class NettyBuildingMessageListener implements NettyMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(NettyBuildingMessageListener.class);

    private NettyBuildingMessageGateway gateway;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyRatesServiceMapper currencyRatesServiceMapper;

    @Autowired
    private CurrencyRatesService currencyRatesService;

    @Autowired
    private ServerCache serverCache;

    @Autowired
    private ScopeService scopeService;

    @Autowired
    private SimpleScopeProcess simpleScopeProcess;

    @Autowired
    private CurrencyRateProcess currencyRateProcess;

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

                    CurrencyRates currencyRatesFromJson = currencyRatesServiceMapper.mapJsonToBean(currencyRatesJson);

                    // Check if we have a new high/low
                    boolean isNewCalculationFromHighNeeded = serverCache.isNewCalculationNeededOrSkip(currencyRatesFromJson, Scope.BUILD_FROM_HIGH);
                    boolean isNewCalculationFromLowNeeded = serverCache.isNewCalculationNeededOrSkip(currencyRatesFromJson, Scope.BUILD_FROM_LOW);

                    // Determine type of scope
                    while (isNewCalculationFromLowNeeded || isNewCalculationFromHighNeeded) {
                        CurrencyRates currentCurrencyRate = currencyRatesServiceMapper.mapJsonToBean(currencyRatesJson);

                        Integer scopeType = (isNewCalculationFromHighNeeded ? Scope.BUILD_FROM_HIGH : (isNewCalculationFromLowNeeded ? Scope.BUILD_FROM_LOW : null));

                        if (scopeType != null) {
                            // reset flags to avoid additional loops
                            if (isNewCalculationFromHighNeeded) {
                                isNewCalculationFromHighNeeded = false;
                            } else if (isNewCalculationFromLowNeeded) {
                                isNewCalculationFromLowNeeded = false;
                            }

                            // Get last scope for currency. if not exists - create it
                            Scope scope = scopeService.findByCurrencyIdAndScopeTypeAndOnDate(currentCurrencyRate.getCurrency().getId(), scopeType, currentCurrencyRate.getTimestamp());

                            // Create a new scope if it's needed and calculate new zones
                            if (scope == null) {
                                scope = scopeService.create(currentCurrencyRate, scopeType);
                                simpleScopeProcess.addScope(scope);
                            }

                            CurrencyRates lastCurrencyRate = currencyRatesService.findByScope(scope);
                            if (lastCurrencyRate == null) {
                                lastCurrencyRate = currentCurrencyRate;
                            } else {
                                currencyRatesService.merge(currentCurrencyRate, lastCurrencyRate);
                            }

                            if (lastCurrencyRate.getScope() == null ||
                                    lastCurrencyRate.getScope().getType() != scope.getType()) {
                                // Save CurrencyRate for next manipulations
                                lastCurrencyRate.setScope(scope);
                            }

                            scope.setCurrencyRate(lastCurrencyRate);
                            serverCache.setScopeCache(scope);

                            currencyRatesService.save(lastCurrencyRate);
                            // Create/calculate scope and zones
                            currencyRateProcess.calculateZones(scope);
                        }
                    }


                    // Determine priority
                    //currencyRateProcess.determinePriority(scope, currentCurrencyRate);

                    // Send response to client
                }
            }
        }
    }



    public void setGateway(NettyBuildingMessageGateway gateway) {
        this.gateway = gateway;
    }

}
