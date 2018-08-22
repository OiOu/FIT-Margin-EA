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
public class OrderModifyListener implements OrderListener {

    @Resource
    private OrderService orderService;

    private NettyBuildingMessageGateway gateway;

    @Override
    @Deprecated
    public void onOrderOpen(Order order, HostPort hostPort) {
        return;
    }

    @Override
    @Deprecated
    public void onOrderClose(Order order, HostPort hostPort) {
        return;
    }

    @Override
    public void onOrderModify(Order order, HostPort hostPort) {

        // Prepare message
        StringBuffer sb = new StringBuffer();
        sb.append(order.getName()).append(Strings.COMMA)
            .append(order.getPriceBreakEvenProfit()).append(Strings.COMMA);

        gateway.sendMessage(RequestsSocket.MODIFY_ORDER, sb.toString(), hostPort);

    }

    public void setGateway(NettyBuildingMessageGateway gateway) {
        this.gateway = gateway;
    }
}
