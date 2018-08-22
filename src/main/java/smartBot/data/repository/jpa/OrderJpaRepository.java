package smartBot.data.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import smartBot.bean.jpa.OrderEntity;

public interface OrderJpaRepository extends CrudRepository<OrderEntity, Integer> {

    @Query("SELECT oe FROM OrderEntity oe WHERE oe.name = ?1")
    OrderEntity findByName(String orderName);
}
