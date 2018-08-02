package smartBot.data.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import smartBot.bean.jpa.PrioritySubTypeEntity;

public interface PrioritySubTypeJpaRepository extends CrudRepository<PrioritySubTypeEntity, Integer> {

    @Query("SELECT p FROM PrioritySubTypeEntity p WHERE p.subtype = ?1")
    PrioritySubTypeEntity getBySubtype(Integer subtype);
}
