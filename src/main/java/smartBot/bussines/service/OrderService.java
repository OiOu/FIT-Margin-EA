package smartBot.bussines.service;

import smartBot.bean.Order;

public interface OrderService extends Service<Order>{

    Order findByName(String orderName);
}
