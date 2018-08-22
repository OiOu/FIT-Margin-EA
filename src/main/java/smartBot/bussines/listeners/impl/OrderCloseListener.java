package smartBot.bussines.listeners.impl;

import org.springframework.stereotype.Component;
import smartBot.bean.Order;
import smartBot.bussines.listeners.OrderListener;
import smartBot.bussines.service.OrderService;
import smartBot.connection.netty.server.common.HostPort;
import smartBot.connection.netty.server.gateway.NettyBuildingMessageGateway;
import smartBot.defines.RequestsSocket;
import smartBot.defines.Strings;

import javax.annotation.Resource;

@Component
public class OrderCloseListener implements OrderListener {

    @Resource
    private OrderService orderService;

    private NettyBuildingMessageGateway gateway;

    @Override
    @Deprecated
    public void onOrderOpen(Order order, HostPort hostPort) {
        return;
    }

    @Override
    public void onOrderClose(Order order, HostPort hostPort) {

        // Prepare message
        StringBuffer sb = new StringBuffer();
        sb.append(order.getName()).append(Strings.COMMA);

        gateway.sendMessage(RequestsSocket.CLOSE_ORDER, sb.toString(), hostPort);

        orderService.save(order);
    }

    @Override
    @Deprecated
    public void onOrderModify(Order order, HostPort hostPort) {
        return;
    }

    public void setGateway(NettyBuildingMessageGateway gateway) {
        this.gateway = gateway;
    }
}
