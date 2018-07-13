package smartBot.data.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import smartBot.bean.jpa.ZoneEntity;

public interface ZoneJpaRepository extends CrudRepository<ZoneEntity, Integer> {

    @Query("SELECT coalesce(ze.id, 0) FROM ZoneEntity ze")
    Integer getLastId();

    @Query("SELECT ze.id FROM ZoneEntity ze JOIN ze.scope s WHERE s.id = ?1")
    Integer getLastId(Integer scopeId);

    @Query("SELECT ze.id FROM ZoneEntity ze JOIN ze.scope s WHERE s.id = ?1 and ze.level = ?2")
    Integer getByScopeIdAndLevel(Integer scopeId, Integer level);
}
