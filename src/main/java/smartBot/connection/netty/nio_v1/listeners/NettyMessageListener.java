package smartBot.connection.netty.nio_v1.listeners;

import smartBot.connection.netty.nio_v1.common.HostPort;
import smartBot.connection.netty.nio_v1.common.NettyMessage;

public interface NettyMessageListener {
    void messageReceived(NettyMessage<?> msg, HostPort hostPort);
}
