package smartBot.data.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import smartBot.bean.jpa.ZoneLevelEntity;

import java.util.List;

public interface ZoneLevelJpaRepository extends CrudRepository<ZoneLevelEntity, Integer> {

    @Query("SELECT zle FROM ZoneLevelEntity zle WHERE zle.id in (?1)")
    List<ZoneLevelEntity> getAllByIds(List<Integer> ids);

    @Query("SELECT zle FROM ZoneLevelEntity zle WHERE zle.id = ?1")
    ZoneLevelEntity getById(Integer id);

    @Query("SELECT zle FROM ZoneLevelEntity zle WHERE zle.k = ?1")
    ZoneLevelEntity getByK(Integer k);
}
