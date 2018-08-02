package smartBot.data.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import smartBot.bean.jpa.PriorityTypeEntity;

public interface PriorityTypeJpaRepository extends CrudRepository<PriorityTypeEntity, Integer> {

    @Query("SELECT p FROM PriorityTypeEntity p WHERE p.type = ?1")
    PriorityTypeEntity getByType(Integer type);
}
