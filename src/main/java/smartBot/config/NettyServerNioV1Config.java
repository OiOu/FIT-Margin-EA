package smartBot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartBot.bussines.service.CurrencyService;
import smartBot.connection.netty.server.gateway.NettyBuildingMessageGateway;
import smartBot.connection.netty.server.handlers.NettyChannelHandler;
import smartBot.connection.netty.server.handlers.NettyDisconnectChannelHandler;
import smartBot.connection.netty.server.listeners.NettyBuildingMessageListener;

import javax.annotation.Resource;

@Configuration
public class NettyServerNioV1Config {

    private static Logger logger = LoggerFactory.getLogger(NettyServerNioV1Config.class);

    private @Value("${netty.niosocket.port}") int nioSocketPort;
    private @Value("${netty.compression.enabled}") boolean isCompressionEnabled;

    @Resource
    private CurrencyService currencyService;

    @Bean
    public NettyBuildingMessageGateway smartBotBuildingServerNettyMessageGateway() throws Exception {

        // init message gateway
        NettyBuildingMessageGateway gateway = new NettyBuildingMessageGateway();
        gateway.setPort(nioSocketPort);
        gateway.setAutoShutdown(true);

        // enable of disable compression
        gateway.setSnappyCompressionEnabled(isCompressionEnabled);

        // init main channel handler
        NettyChannelHandler channelHandler = new NettyChannelHandler(true);
        channelHandler.setGateway(gateway);

        gateway.setHandler(channelHandler);
        gateway.addHandler(new NettyDisconnectChannelHandler());

        // init message listener
        NettyBuildingMessageListener listener = getBuildingServerNettyMessageListener();
        listener.setGateway(gateway);
        gateway.addMessageListener(listener);

        gateway.connect();

        return gateway;

    }

    @Bean
    public NettyBuildingMessageListener getBuildingServerNettyMessageListener() {
        return new NettyBuildingMessageListener();
    }

}

