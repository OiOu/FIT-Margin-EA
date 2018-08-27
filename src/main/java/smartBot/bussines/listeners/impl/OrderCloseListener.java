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
import java.util.List;

@Component
public class OrderCloseListener implements OrderListener {

    @Resource
    private OrderService orderService;

    @Resource
    private ServerCache serverCache;

    private NettyBuildingMessageGateway gateway;

    @Override
    @Deprecated
    public void onOrderOpen(Order order, HostPort hostPort) {
        return;
    }

    @Override
    @Deprecated
    public void onActivate(Order order, HostPort hostPort) {
        return;
    }

    @Override
    public void onOrderClose(Order order, HostPort hostPort) {

        orderService.save(order);

        // Prepare message
        StringBuffer sb = new StringBuffer();
        sb.append(order.getName()).append(Strings.COMMA);

        // TODO add list support for closing by one request
        gateway.sendMessage(RequestsSocket.CLOSE_ORDER, sb.toString(), hostPort);

    }

    @Override
    public void onOrderCloseAll(Integer currencyId, Integer subType, HostPort hostPort) {
        List<Order> orderList = orderService.deleteAllNotActivated(currencyId, subType);

        if (orderList != null && orderList.size() > 0) {
            StringBuffer sb = new StringBuffer();

            orderList.forEach(order -> {
                serverCache.removeOrderFromCache(order);
                sb.append(order.getName()).append(Strings.COMMA);
            });

            String result = sb.toString();
            if (result.endsWith(Strings.COMMA)) {
                result = result.substring(0, result.length() - 1);
            }

            // TODO update Client with deletion all
            gateway.sendMessage(RequestsSocket.CLOSE_ORDERS, result, hostPort);
        }
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
