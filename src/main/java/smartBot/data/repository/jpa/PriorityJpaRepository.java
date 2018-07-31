package smartBot.data.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import smartBot.bean.jpa.PriorityEntity;

public interface PriorityJpaRepository extends CrudRepository<PriorityEntity, Integer> {

    @Query("SELECT p FROM PriorityEntity p JOIN p.currency c " +
            " WHERE c.id = ?1 " +
                " AND p.subtype = ?2 " +
                " AND p.startDate IS NOT NULL")
    PriorityEntity findByCurrencyIdAndSubType(Integer currencyId, Integer prioritySubType);
}
