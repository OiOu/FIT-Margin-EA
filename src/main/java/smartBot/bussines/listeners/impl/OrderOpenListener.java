package smartBot.bussines.listeners.impl;

import org.springframework.stereotype.Component;
import smartBot.bean.Order;
import smartBot.bussines.listeners.OrderListener;
import smartBot.bussines.service.OrderService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.connection.netty.server.common.HostPort;
import smartBot.connection.netty.server.gateway.NettyBuildingMessageGateway;
import smartBot.defines.RequestsSocket;
import smartBot.defines.Strings;

import javax.annotation.Resource;

@Component
public class OrderOpenListener implements OrderListener {

    private NettyBuildingMessageGateway gateway;

    @Resource
    private OrderService orderService;

    @Resource
    private ServerCache serverCache;

    @Override
    @Deprecated
    public void onActivate(Order order, HostPort hostPort) {
        return;
    }

    @Override
    public void onOrderOpen(Order order, HostPort hostPort) {
        // Prepare message
        StringBuffer sb = new StringBuffer();
        sb.append(order.getSubtype()).append(Strings.COMMA)
                .append(order.getName()).append(Strings.COMMA)
                .append(order.getPrice()).append(Strings.COMMA)
                .append(order.getPriceStopLoss()).append(Strings.COMMA)
                .append(order.getPriceTakeProfit());

        gateway.sendMessage(RequestsSocket.OPEN_ORDER, sb.toString(), hostPort);

        order = orderService.save(order);

        serverCache.setOrderToCache(order);
    }

    @Override
    @Deprecated
    public void onOrderCloseAll(Integer currencyId, Integer operation, HostPort hostPort) {
        return;
    }

    @Override
    @Deprecated
    public void onOrderClose(Order order, HostPort hostPort) {
        return;
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
