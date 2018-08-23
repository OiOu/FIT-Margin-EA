package smartBot.bussines.listeners;

import smartBot.bean.Order;
import smartBot.connection.netty.server.common.HostPort;

public interface OrderListener {

   void onOrderOpen(Order order, HostPort hostPort);

   void onOrderClose(Order order, HostPort hostPort);

   void onOrderModify(Order order, HostPort hostPort);

   void onActivate(Order order, HostPort hostPort);

   void onOrderCloseAll(Integer currencyId, Integer operation, HostPort hostPort);
}


