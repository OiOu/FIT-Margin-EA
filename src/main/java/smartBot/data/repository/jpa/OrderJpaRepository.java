package smartBot.data.repository.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import smartBot.bean.jpa.OrderEntity;

import java.util.List;

public interface OrderJpaRepository extends CrudRepository<OrderEntity, Integer> {

    @Query("SELECT oe FROM OrderEntity oe WHERE oe.name = ?1")
    OrderEntity findByName(String orderName);

    @Query("SELECT oe FROM OrderEntity oe WHERE oe.activated = true")
    List<OrderEntity> findAllActivated();

    @Query("SELECT oe FROM OrderEntity oe JOIN oe.currency c WHERE oe.activated = false AND c.id = ?1 AND oe.subtype = ?2")
    List<OrderEntity> findAllNotActivated(Integer currencyId, Integer subType);

    @Modifying
    @Query("DELETE FROM OrderEntity oe WHERE oe.activated = false AND oe.currency.id = ?1 AND oe.subtype = ?2")
    void deleteAllNotActivated(Integer currencyId, Integer subType);
}
