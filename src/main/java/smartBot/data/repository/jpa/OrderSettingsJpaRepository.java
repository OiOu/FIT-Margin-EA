package smartBot.data.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import smartBot.bean.jpa.OrderSettingsEntity;

public interface OrderSettingsJpaRepository extends CrudRepository<OrderSettingsEntity, Integer> {

    @Query("SELECT os FROM OrderSettingsEntity os JOIN os.currency c WHERE c.id = ?1")
    OrderSettingsEntity getByCurrencyId(Integer currencyId);
}
