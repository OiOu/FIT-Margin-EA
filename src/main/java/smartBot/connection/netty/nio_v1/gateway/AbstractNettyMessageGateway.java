package smartBot.connection.netty.nio_v1.gateway;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartBot.connection.netty.nio_v1.common.HostPort;
import smartBot.connection.netty.nio_v1.common.NettyMessage;
import smartBot.connection.netty.nio_v1.exceptions.MessageException;
import smartBot.connection.netty.nio_v1.handlers.NettyChannelHandler;
import smartBot.connection.netty.nio_v1.listeners.NettyMessageListener;

import java.util.*;

public abstract class AbstractNettyMessageGateway {

    private static final Logger log = LoggerFactory.getLogger(AbstractNettyMessageGateway.class);

    private List<NettyMessageListener> listeners = Collections.synchronizedList(new ArrayList<NettyMessageListener>());

    private boolean autoShutdown;

    private int port;

    private int maxMessageSize = Integer.MAX_VALUE;

    private NettyChannelHandler handler;

    private List<ChannelHandler> handlers = new ArrayList<>();

    /**
     * Broadcasts a {@link NettyMessage} to all connected clients from the server
     * or to the server from a client. Use this method for all STOMP messages.
     *
     * @param message
     *          the message
     * @throws MessageException
     *           the intercept exception
     */
    public void broadcastMessage(NettyMessage<?> message) throws MessageException {
        broadcastMessage(message.toStompMessage(true));
    }

    /*
     * Broadcasts the specified String to all connections. Included for STOMP
     * implementations which accept custom message types. Use for all non-STOMP
     * messages.
     */
    private void broadcastMessage(String stompMessage) {
        getHandler().broadcastMessage(stompMessage);
    }

    /**
     * Sends a {@link NettyMessage} to the specified {@link HostPort}. Use this
     * method for all STOMP messages.
     *
     * @param message
     *          the message
     * @param hostPort
     *          the host port
     * @throws MessageException
     *           the intercept exception
     */
    public void sendMessage(NettyMessage<?> message, HostPort hostPort) throws MessageException {
        sendMessage(message.toStompMessage(true), hostPort);
    }

    public void sendMessages(List<? extends NettyMessage> messages, HostPort hostPort) {
        List<String> strMessages = new ArrayList<>();
        for (NettyMessage message : messages) {
            try {
                strMessages.add(message.toStompMessage(true));
            } catch (MessageException e) {
                e.printStackTrace();
            }
        }
        sendMessage(strMessages, hostPort);
    }

    /*
     * Sends the specified String to the specified {@link HostPort}. Included for
     * STOMP implementations which accept custom message types. Use for all
     * non-STOMP messages.
     */
    public void sendMessage(String stompMessage, HostPort hostPort) {
        getHandler().sendMessage(stompMessage, hostPort);
    }

    public void sendMessage(List<String> stompMessage, HostPort hostPort) {
        getHandler().sendMessage(stompMessage, hostPort);
    }

    /*
     * Returns true if a connection exists and is active.
     */
    public boolean isConnected(HostPort hostPort) {
        return getHandler().isConnected(hostPort);
    }

    /*
     * Gets the connected host ports.
     */
    public Set<HostPort> getConnectedHostPorts() {
        return getHandler().getConnectedHostPorts();
    }

    /**
     * Gets the stampy channel handler.
     *
     * @return the stampy channel handler
     */
    public NettyChannelHandler getHandler() {
        return handler;
    }

    /**
     * Sets the stampy channel handler.
     *
     * @param channelHandler
     *          the new stampy channel handler
     */
    public void setHandler(NettyChannelHandler channelHandler) {
        this.handler = channelHandler;
    }

    /**
     * Adds the Channel Handler for inclusion in the created Channel. Note that on
     * the server the handler will be shared across all connections, and as such
     * must be able to act as a singleton ie. no {@link io.netty.handler.codec.http2.Http2ConnectionHandler.FrameDecoder}s here.
     *
     * @param handler
     *          the handler
     */
    public void addHandler(ChannelHandler handler) {
        handlers.add(handler);
    }

    /**
     * Removes the handler.
     *
     * @param handler
     *          the handler
     */
    public void removeHandler(ChannelHandler handler) {
        handlers.remove(handler);
    }

    /*
     * Adds the handlers.
     *
     * @param pipeline the pipeline
     */
    protected void addHandlers(ChannelPipeline pipeline) {
        for (ChannelHandler handler : handlers) {
            pipeline.addLast(handler.toString(), handler);
        }
    }

    /**
     * Notify listeners of received messages.
     *
     * @param msg
     *          the msg
     * @param hostPort
     *          the host port
     * @throws Exception
     *           the exception
     */
    public void notifyMessageListeners(NettyMessage<?> msg, HostPort hostPort) {
        for (NettyMessageListener listener : listeners) {
//            if (isForType(listener.getMessageTypes(), sm.getMessageType()) && listener.isForMessage(sm)) {
                log.trace("Evaluating message {} with listener {}", msg, listener);
                listener.messageReceived(msg, hostPort);
//            }
        }
    }

    /**
     * Adds the message listener.
     *
     * @param listener
     *          the listener
     */
    public final void addMessageListener(NettyMessageListener listener) {
        listeners.add(listener);
    }

    public final void addMessageListener(NettyMessageListener listener, int idx) {
        listeners.add(idx, listener);
    }

    /**
     * Removes the message listener.
     *
     * @param listener
     *          the listener
     */
    public void removeMessageListener(NettyMessageListener listener) {
        listeners.remove(listener);
    }

    /**
     * Clear message listeners.
     */
    public void clearMessageListeners() {
        listeners.clear();
    }

    /**
     * Sets the listeners.
     *
     * @param listeners
     *          the new listeners
     */
    public void setListeners(Collection<NettyMessageListener> listeners) {
        this.listeners.addAll(listeners);
    }

    /**
     * Closes the connection to the STOMP server or client.
     *
     * @param hostPort
     *          the host port
     */
    public abstract void closeConnection(HostPort hostPort);

    /**
     * Connects to a STOMP server or client as specified by configuration.
     *
     * @throws Exception
     *           the exception
     */
    public abstract void connect() throws Exception;

    /**
     * Shuts down the underlying connection technology.
     *
     * @throws Exception
     *           the exception
     */
    public abstract void shutdown() throws Exception;

    /**
     * If true the gateway will shut down when all sessions are terminated.
     * Typically clients will be set to true, servers to false (the default).
     *
     * @return true, if is auto shutdown
     */
    public boolean isAutoShutdown() {
        return autoShutdown;
    }

    /**
     * Sets the auto shutdown.
     *
     * @param autoShutdown
     *          the new auto shutdown
     */
    public void setAutoShutdown(boolean autoShutdown) {
        this.autoShutdown = autoShutdown;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxMessageSize() {
        return maxMessageSize;
    }

    public void setMaxMessageSize(int maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
    }

}
