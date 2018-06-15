package smartBot.connection.netty.server.listeners;

import smartBot.connection.netty.server.common.HostPort;
import smartBot.connection.netty.server.common.NettyMessage;

public interface NettyMessageListener {
    void messageReceived(NettyMessage<?> msg, HostPort hostPort);
}
