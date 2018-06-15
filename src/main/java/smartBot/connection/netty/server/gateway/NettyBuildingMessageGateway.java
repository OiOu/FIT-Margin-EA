package smartBot.connection.netty.server.gateway;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.compression.SnappyFrameDecoder;
import io.netty.handler.codec.compression.SnappyFrameEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartBot.connection.netty.server.common.HostPort;
import smartBot.connection.netty.server.exceptions.MessageException;
import smartBot.connection.netty.server.messages.Message;
import smartBot.defines.Constants;


public class NettyBuildingMessageGateway extends AbstractNettyMessageGateway {
    private static final Logger log = LoggerFactory.getLogger(NettyBuildingMessageGateway.class);

    private Channel server;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private boolean snappyCompressionEnabled = false;

    private ServerBootstrap init() {

        boolean isLinux = SystemUtils.IS_OS_LINUX;

        if (isLinux) {
            bossGroup = new EpollEventLoopGroup(1);
            workerGroup = new EpollEventLoopGroup();
        } else {
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
        }

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
            .channel(isLinux ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
            .handler(new LoggingHandler())
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();

                    // Enable stream compression
                    if (snappyCompressionEnabled) {
                        pipeline.addLast(new SnappyFrameEncoder());
                        pipeline.addLast(new SnappyFrameDecoder());
                    }

                    pipeline.addLast(new StringDecoder(Constants.CHARSET));
                    pipeline.addLast(new StringEncoder(Constants.CHARSET));

                    // and then business logic.
                    addHandlers(pipeline);
                    pipeline.addLast(getHandler());
                }
            });
        return b;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * asia.stampy.common.gateway.AbstractStampyMessageGateway#closeConnection
     * (asia.stampy.common.gateway.HostPort)
     */
    @Override
    public void closeConnection(HostPort hostPort) {
        getHandler().close(hostPort);
    }

    /*
     * (non-Javadoc)
     *
     * @see asia.stampy.common.gateway.AbstractStampyMessageGateway#connect()
     */
    @Override
    public void connect() throws Exception {
        if (server == null) {
            ServerBootstrap bootstrap = init();
            server = bootstrap.bind(getPort()).sync().channel();
            log.info("Netty server was started on {} port", getPort());
        } else if (server.isActive()) {
            log.warn("Already connected");
        } else {
            log.error("Acceptor in unrecognized state: isOpen {}, isActive {}, ", server.isOpen(), server.isActive());
        }
    }

    public void sendMessage(String destination, String body, HostPort hostPort) {

        Message message = new Message(destination);
        message.setBody(body);

        try {
            this.sendMessage(message, hostPort);
        } catch (MessageException e) {
            log.error(e.getMessage());
        }

    }

    public void sendPongMessage(HostPort hostPort) {
        sendMessage(Constants.BOM + Message.PONG + Constants.EOM, hostPort);
    }

    /*
     * (non-Javadoc)
     *
     * @see asia.stampy.common.gateway.AbstractStampyMessageGateway#shutdown()
     */
    @Override
    public void shutdown() throws Exception {
        if (server == null || !server.isActive()) return;
        ChannelFuture cf = server.close();
        cf.awaitUninterruptibly();
        server.closeFuture().sync();
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        server = null;
        log.info("Server has been shut down");
    }

    public void setSnappyCompressionEnabled(boolean snappyCompressionEnabled) {
        this.snappyCompressionEnabled = snappyCompressionEnabled;
    }
}
