package smartBot.bussines.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.OrderSettings;
import smartBot.bean.jpa.OrderSettingsEntity;
import smartBot.bussines.service.OrderSettingsService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.bussines.service.mapping.OrderSettingsServiceMapper;
import smartBot.data.repository.jpa.OrderSettingsJpaRepository;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class OrderSettingsServiceImpl implements OrderSettingsService {
    private static final Log logger = LogFactory.getLog(OrderSettingsServiceImpl.class);

    @Resource
    private OrderSettingsJpaRepository orderSettingsJpaRepository;

    @Resource
    private OrderSettingsServiceMapper orderSettingsServiceMapper;

    @Autowired
    private ServerCache serverCache;

    @Override
    public OrderSettings getByCurrencyId(Integer currencyId) {
        // Try to get currency from cache to avoid additional DB query
        OrderSettings orderSettings = serverCache.getOrderSettingsFromCache( currencyId );

        if (orderSettings == null) {
            OrderSettingsEntity orderSettingsEntity = orderSettingsJpaRepository.getByCurrencyId(currencyId);
            if (orderSettingsEntity != null) {
                orderSettings = orderSettingsServiceMapper.mapEntityToBean(orderSettingsEntity);
                serverCache.setOrderSettingsToCache(currencyId, orderSettings);
            } else {
                logger.warn("OrderSettings for currency: " + currencyId + " was not found");
            }

        }
        return orderSettings;
    }

    @Override
    public OrderSettings findById(Integer id) {
        return null;
    }

    @Override
    public List<OrderSettings> findAll() {
        return null;
    }

    @Override
    public OrderSettings save(OrderSettings bean) {
        return null;
    }

    @Override
    public void delete(OrderSettings bean) {

    }

    @Override
    public void delete(Integer id) {

    }
}
