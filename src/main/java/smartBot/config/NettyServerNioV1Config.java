package smartBot.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartBot.bussines.service.CurrencyService;
import smartBot.connection.netty.nio_v1.gateway.NettyBuildingServerMessageGateway;
import smartBot.connection.netty.nio_v1.handlers.NettyChannelHandler;
import smartBot.connection.netty.nio_v1.handlers.NettyDisconnectChannelHandler;
import smartBot.connection.netty.nio_v1.listeners.NettyBuildingServerMessageListener;

import javax.annotation.Resource;

@Configuration
public class NettyServerNioV1Config {

    private static Log logger = LogFactory.getLog(NettyServerNioV1Config.class);

    private @Value("${netty.niosocket.port}") int nioSocketPort;
    private @Value("${netty.compression.enabled}") boolean isCompressionEnabled;

    @Resource
    private CurrencyService currencyService;

    @Bean
    public NettyBuildingServerMessageGateway smartBotBuildingServerNettyMessageGateway() throws Exception {

        // init message gateway
        NettyBuildingServerMessageGateway gateway = new NettyBuildingServerMessageGateway();
        gateway.setPort(nioSocketPort);
        gateway.setAutoShutdown(true);

        // enable of disable compression
        gateway.setSnappyCompressionEnabled(isCompressionEnabled);

        // init main channel handler
        NettyChannelHandler channelHandler = new NettyChannelHandler(true);
        channelHandler.setGateway(gateway);

        gateway.setHandler(channelHandler);
        gateway.addHandler(new NettyDisconnectChannelHandler(currencyService));

        // init message listener
        NettyBuildingServerMessageListener listener = getBuildingServerNettyMessageListener();
        listener.setGateway(gateway);
        gateway.addMessageListener(listener);

        gateway.connect();

        return gateway;

    }

    @Bean
    public NettyBuildingServerMessageListener getBuildingServerNettyMessageListener() {
        return new NettyBuildingServerMessageListener();
    }

}

