package smartBot.connection.netty.nio_v1.listeners;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartBot.connection.netty.nio_v1.common.HostPort;
import smartBot.connection.netty.nio_v1.common.NettyMessage;
import smartBot.connection.netty.nio_v1.gateway.NettyBuildingServerMessageGateway;
import smartBot.connection.netty.nio_v1.messages.Message;
import smartBot.connection.netty.nio_v1.messages.MessageHeader;
import smartBot.connection.netty.nio_v1.messages.PingMessage;

public class NettyBuildingServerMessageListener implements NettyMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(NettyBuildingServerMessageListener.class);

    private NettyBuildingServerMessageGateway gateway;

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

        if (message instanceof PingMessage) {
            gateway.sendPongMessage(hostPort);
            return;
        }

        String strDestination = msg.getHeader().getHeaderValue(MessageHeader.DESTINATION);
        if (strDestination != null) {

            // Asset online status message
/*            if (RequestsSocket.ASSET_STATUS_ONLINE_v2_0.equals(strDestination)) {

            }*/
        }
    }

    public void setGateway(NettyBuildingServerMessageGateway gateway) {
        this.gateway = gateway;
    }

    private String getFactoryId(String strDestination) {
        String factory_id = "";
        if(strDestination!=null){
            int index = strDestination.lastIndexOf(".");
            if(strDestination.length()>=index+1)
                factory_id = strDestination.substring(index+1);
        }
        return factory_id;
    }
}
