package smartBot.connection.netty.server.handlers;

import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartBot.connection.netty.server.common.HostPort;
import smartBot.connection.netty.server.common.NettyMessage;
import smartBot.connection.netty.server.exceptions.MessageException;
import smartBot.connection.netty.server.gateway.AbstractNettyMessageGateway;
import smartBot.connection.netty.server.parser.MessageParser;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@ChannelHandler.Sharable
public class NettyChannelHandler extends SimpleChannelInboundHandler<String> {

    private final Logger logger = LoggerFactory.getLogger(NettyChannelHandler.class);

    private MessageParser parser = new MessageParser();

    private AbstractNettyMessageGateway gateway;

    private Map<HostPort, Executor> executors = new ConcurrentHashMap<>();

    private Map<HostPort, Channel> sessions = new ConcurrentHashMap<HostPort, Channel>();

    public NettyChannelHandler(boolean autoRelease) {
        super(autoRelease);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, final String msg) throws Exception {

        final HostPort hostPort = createHostPort(ctx);

        logger.info("channels: [");
        for (Channel channel : sessions.values()) {
            logger.info("channel: " + channel.remoteAddress().toString() + " open: " + channel.isOpen() + " active: " + channel.isActive() + " registered: " + channel.isRegistered() );
        }
        logger.info("]");

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                asyncProcessing(hostPort, msg);
            }
        };
        getExecutor(hostPort).execute(runnable);
    }

    /**
     * Creates the host port.
     *
     * @param ctx
     *          the ctx
     * @return the host port
     */
    protected HostPort createHostPort(ChannelHandlerContext ctx) {
        return new HostPort((InetSocketAddress) ctx.channel().remoteAddress());
    }

    /**
     * Gets the connected host ports.
     *
     * @return the connected host ports
     */
    public Set<HostPort> getConnectedHostPorts() {
        return Collections.unmodifiableSet(sessions.keySet());
    }

    /**
     * Checks if is connected.
     *
     * @param hostPort
     *          the host port
     * @return true, if is connected
     */
    public boolean isConnected(HostPort hostPort) {
        return sessions.containsKey(hostPort);
    }

    /**
     * Broadcast message.
     *
     * @param message
     *          the message
     */
    public void broadcastMessage(String message) {
        for (Channel channel : sessions.values()) {
           sendMessage(message, null, channel);
        }
    }

    /**
     * Send message.
     *
     * @param message
     *          the message
     * @param hostPort
     *          the host port
     */
    public void sendMessage(String message, HostPort hostPort) {
        sendMessage(message, hostPort, sessions.get(hostPort));
    }

    public void sendMessage(List<String> message, HostPort hostPort) {
        sendMessage(message, hostPort, sessions.get(hostPort));
    }

    private synchronized void sendMessage(String message, HostPort hostPort, Channel channel) {
        if (channel == null || !channel.isActive()) {
            logger.error(
                "Channel is not connected, cannot send message {}...",
                message.substring(0, Math.min(message.length() - 1, 100)));
            return;
        }

        channel.writeAndFlush(message);
    }

    private synchronized void sendMessage(List<String> messages, HostPort hostPort, Channel channel) {
        if (channel == null || !channel.isActive()) {
            logger.error("Channel is not connected, cannot send message {}...");
            return;
        }

        for (String message : messages) {
            channel.write(message);
        }
        channel.flush();
    }

    /**
     * Close.
     *
     * @param hostPort
     *          the host port
     */
    public void close(HostPort hostPort) {
        if (!isConnected(hostPort)) {
            logger.warn("{} is already closed");
            return;
        }

        Channel channel = sessions.get(hostPort);
        ChannelFuture cf = channel.close();
        cf.awaitUninterruptibly();
        logger.info("Session for {} has been closed", hostPort);
    }

    /**
     * Once simple validation has been performed on the received message a
     * Runnable is executed by a single thread executor. This pulls the messages
     * off the thread NETTY uses and ensures the messages are processed in the
     * order they are received.
     *
     * @param hostPort
     *          the host port
     * @param msg
     *          the msg
     */
    protected void asyncProcessing(HostPort hostPort, String msg) {
        NettyMessage<?> sm = null ;
        try {
            logger.info(msg);
            sm = getParser().parseMessage(msg);

            getGateway().notifyMessageListeners(sm, hostPort);

            //broadcastMessage(msg);
        } catch (MessageException e) {
            logger.error(e.getMessage(), e.getStompMessage());
        }
    }

    /**
     * <strong>Be aware that this event is fired from within the Boss-Thread so
     * you should not execute any heavy operation in there as it will block the
     * dispatching to other workers!</strong>
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        HostPort hostPort = createHostPort(ctx);
        sessions.put(hostPort, ctx.channel());
        executors.put(hostPort, Executors.newSingleThreadExecutor());
        logger.debug("Channel active: id=" + hostPort);
        super.channelActive(ctx);
    }

    /**
     * <strong>Be aware that this event is fired from within the Boss-Thread so
     * you should not execute any heavy operation in there as it will block the
     * dispatching to other workers!</strong>
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        HostPort hostPort = createHostPort(ctx);
        sessions.remove(hostPort);
        executors.remove(hostPort);
        logger.debug("Channel inactive: id=" + hostPort);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        HostPort hostPort = createHostPort(ctx);
        logger.error("Unexpected Netty exception for " + hostPort, cause);
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * Gets the executor.
     *
     * @return the executor
     * @param hostPort
     */
    public Executor getExecutor(HostPort hostPort) {
        return executors.get(hostPort);
    }

    /**
     * Gets the gateway.
     *
     * @return the gateway
     */
    public AbstractNettyMessageGateway getGateway() {
        return gateway;
    }

    /**
     * Sets the gateway.
     *
     * @param gateway
     *          the new gateway
     */
    public void setGateway(AbstractNettyMessageGateway gateway) {
        this.gateway = gateway;
    }

    /**
     * Gets the parser.
     *
     * @return the parser
     */
    public MessageParser getParser() {
        return parser;
    }

    /**
     * Sets the parser.
     *
     * @param parser
     *          the new parser
     */
    public void setParser(MessageParser parser) {
        this.parser = parser;
    }
}
