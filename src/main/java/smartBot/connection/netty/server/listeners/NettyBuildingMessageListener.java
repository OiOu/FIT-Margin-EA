package smartBot.connection.netty.server.listeners;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import smartBot.bean.Currency;
import smartBot.bussines.service.CurrencyService;
import smartBot.connection.netty.server.common.HostPort;
import smartBot.connection.netty.server.common.NettyMessage;
import smartBot.connection.netty.server.gateway.NettyBuildingMessageGateway;
import smartBot.connection.netty.server.messages.Message;
import smartBot.connection.netty.server.messages.MessageHeader;
import smartBot.connection.netty.server.messages.PingMessage;
import smartBot.defines.RequestsSocket;

import java.util.List;
import java.util.stream.Collectors;

public class NettyBuildingMessageListener implements NettyMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(NettyBuildingMessageListener.class);

    private NettyBuildingMessageGateway gateway;

    @Autowired
    private CurrencyService currencyService;

    @Override
    public void messageReceived(NettyMessage<?> msg, HostPort hostPort) {

        Message message = (Message) msg;
        String strBody = message.getBody();

        if (logger.isDebugEnabled()) {

            logger.debug("Received Netty message size: {} from: {}  sending time {} ",
                    /*msg,*/
                    strBody != null ? strBody.length() : 0,
                    hostPort,
                    new DateTime().getMillis() -
                        (new DateTime(msg.getHeader().getHeaderValue(MessageHeader.TIMESTAMP)).getMillis()));
        }

        // TODO: ALL WORK SHOULD BE DONE` HERE

        if (message instanceof PingMessage) {
            gateway.sendPongMessage(hostPort);
            return;
        }

        String strDestination = msg.getHeader().getHeaderValue(MessageHeader.DESTINATION);
        if (strDestination != null) {

            // Asset online status message
            if (RequestsSocket.CURRENCIES_GET_ALL_LIST.equals(strDestination)) {
                List<Currency> currencyEntityList = currencyService.findAll();

                String allCurrenciesNames = currencyEntityList.stream().map(p -> p.getShortName()).collect(Collectors.joining(","));

                gateway.sendMessage(
                        RequestsSocket.CURRENCIES_GET_ALL_LIST,
                        allCurrenciesNames,
                        hostPort
                );
            }
        }
    }

    public void setGateway(NettyBuildingMessageGateway gateway) {
        this.gateway = gateway;
    }

}
