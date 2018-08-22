package smartBot.bussines.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import smartBot.bean.Order;
import smartBot.bean.jpa.OrderEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    public OrderServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from '{@link OrderEntity}' to '{@link Order}'
     * @param orderEntity
     */
    public Order mapEntityToBean(OrderEntity orderEntity) {
        if(orderEntity == null) {
            return null;
        }
        //--- Generic mapping
        Order order = map(orderEntity, Order.class);
        return order;
    }

    /**
     * Mapping from '{@link Order}' to '{@link OrderEntity}'
     * @param order
     * @param orderEntity
     */
    public void mapBeanToEntity(Order order, OrderEntity orderEntity) {
        if(order == null) {
            return;
        }

        //--- Generic mapping
        map(order, orderEntity);
    }

    public  List<OrderEntity> mapBeansToEntities(List<Order> marginRatesList) {
        if(marginRatesList == null) {
            return null;
        }

        //--- Generic mapping
        return map(marginRatesList, OrderEntity.class);
    }
    /**
     * Mapping from '{@link List}<{@link Order}'> to '{@link List}<{@link OrderEntity}>'
     * @param marginRatesEntities
     * @return List<Order>
     */
    public List<Order> mapEntitiesToBeans(List<OrderEntity> marginRatesEntities) {
        if(marginRatesEntities == null || marginRatesEntities.size() == 0) {
            return new ArrayList<>();
        }

        //--- Generic mapping
        ArrayList<Order> marginRatesArrayList = new ArrayList<>();
        for (OrderEntity orderEntity: marginRatesEntities)
            marginRatesArrayList.add(map(orderEntity, Order.class));

        return marginRatesArrayList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ModelMapper getModelMapper() {
        return modelMapper;
    }

    protected void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

}
