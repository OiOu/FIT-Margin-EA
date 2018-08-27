package smartBot.bussines.listeners.impl;

import org.springframework.stereotype.Component;
import smartBot.bean.Order;
import smartBot.bussines.listeners.OrderListener;
import smartBot.bussines.service.OrderService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.connection.netty.server.common.HostPort;
import smartBot.connection.netty.server.gateway.NettyBuildingMessageGateway;

import javax.annotation.Resource;

@Component
public class OrderActivateListener implements OrderListener {

    private NettyBuildingMessageGateway gateway;

    @Resource
    private OrderService orderService;

    @Resource
    private ServerCache serverCache;

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
    @Deprecated
    public void onOrderModify(Order order, HostPort hostPort) {
        return;
    }

    @Override
    @Deprecated
    public void onOrderCloseAll(Integer currencyId, Integer operation, HostPort hostPort) {
        return;
    }

    @Override
    public void onActivate(Order order, HostPort hostPort) {
        orderService.save(order);
    }

    public void setGateway(NettyBuildingMessageGateway gateway) {
        this.gateway = gateway;
    }
}
