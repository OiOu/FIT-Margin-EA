package smartBot.data.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import smartBot.bean.jpa.ScopeEntity;

import java.util.Date;
import java.util.List;

public interface ScopeJpaRepository extends CrudRepository<ScopeEntity, Integer> {

    @Query("SELECT se FROM ScopeEntity se JOIN se.currency c WHERE c.id = ?1 and se.type = ?2 ORDER BY se.timestampFrom DESC")
    ScopeEntity findLastByCurrencyIdAndScopeType(Integer currencyId, Integer scopeType);

    @Query("SELECT se FROM ScopeEntity se JOIN se.currency c WHERE c.id = ?1 and se.type = ?2 and ?3 >= se.timestampFrom")
    List<ScopeEntity> findByCurrencyIdAndScopeTypeAndTimestamp(Integer currencyId, Integer scopeType, Date onDate);

    @Query("SELECT coalesce(se.id, 0) FROM ScopeEntity se")
    Integer getLastId();

    @Query("SELECT se.id FROM ScopeEntity se JOIN se.currency c WHERE c.id = ?1 and se.type = ?2")
    Integer getLastId(Integer currencyId, Integer scopeType);
}
