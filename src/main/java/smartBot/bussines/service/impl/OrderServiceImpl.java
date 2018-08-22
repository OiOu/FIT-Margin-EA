package smartBot.bussines.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.Order;
import smartBot.bean.jpa.OrderEntity;
import smartBot.bussines.service.OrderService;
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

    @Override
    public Order findById(Integer id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
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
        }
        return;
    }

    @Override
    public void delete(Integer id) {

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
