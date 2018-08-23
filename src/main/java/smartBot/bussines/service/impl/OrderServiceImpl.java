package smartBot.bussines.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.Order;
import smartBot.bean.jpa.OrderEntity;
import smartBot.bussines.service.OrderService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.bussines.service.mapping.OrderServiceMapper;
import smartBot.data.repository.jpa.OrderJpaRepository;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class OrderServiceImpl implements OrderService {
    private static final Log logger = LogFactory.getLog(OrderServiceImpl.class);

    @Resource
    private OrderJpaRepository orderJpaRepository;

    @Resource
    private OrderServiceMapper orderServiceMapper;

    @Resource
    private ServerCache serverCache;


    @Override
    public Order findById(Integer id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        List<OrderEntity> orderEntities = orderJpaRepository.findAllActivated();
        return orderServiceMapper.mapEntitiesToBeans(orderEntities);
    }

    @Override
    public Order save(Order order) {
        if (order != null) {
            OrderEntity orderEntity = new OrderEntity();

            orderServiceMapper.mapBeanToEntity(order, orderEntity);
            orderEntity = orderJpaRepository.save(orderEntity);
            order = orderServiceMapper.mapEntityToBean(orderEntity);
        }
        return order;
    }

    @Override
    public void delete(Order order) {
        if (order != null) {
            OrderEntity orderEntity = new OrderEntity();

            orderServiceMapper.mapBeanToEntity(order, orderEntity);
            // We should save order in DB and remove it from cache
            // TODO Trigger will remove if reason will be not null
            orderJpaRepository.save(orderEntity);

            serverCache.removeOrderFromCache(order);
        }
        return;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Order> deleteAllNotActivated(Integer currencyId, Integer subType) {

        List<OrderEntity> orderEntities = orderJpaRepository.findAllNotActivated(currencyId, subType);
        orderJpaRepository.deleteAllNotActivated(currencyId, subType);
        List<Order> orders = orderServiceMapper.mapEntitiesToBeans(orderEntities);

        orders.forEach(order -> serverCache.removeOrderFromCache(order));

        return orders;
    }

    @Override
    public Order findByName(String orderName) {
        Order order = null;
        if (StringUtils.isNotEmpty(orderName)) {
            OrderEntity orderEntity = orderJpaRepository.findByName(orderName);
            order = orderServiceMapper.mapEntityToBean(orderEntity);
        }
        return order;
    }
}
