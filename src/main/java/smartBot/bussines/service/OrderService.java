package smartBot.bussines.service;

import smartBot.bean.Order;

import java.util.List;

public interface OrderService extends Service<Order>{

    Order findByName(String orderName);

    List<Order> deleteAllNotActivated(Integer currencyId, Integer operation);

}
