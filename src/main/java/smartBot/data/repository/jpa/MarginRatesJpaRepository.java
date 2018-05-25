package smartBot.data.repository.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import smartBot.bean.jpa.MarginRatesEntity;

import java.util.List;

public interface MarginRatesJpaRepository extends CrudRepository<MarginRatesEntity, Integer> {

    @Query("SELECT mre FROM MarginRatesEntity mre WHERE mre.id in (?1)")
    List<MarginRatesEntity> findByIds(List<Integer> ids);

    @Query("SELECT mre FROM MarginRatesEntity mre WHERE mre.id = ?1")
    MarginRatesEntity getById(Integer id);

    @Query("SELECT mre FROM MarginRatesEntity mre JOIN mre.currency c WHERE c.shortName = ?1")
    List<MarginRatesEntity> findAllByCurrencyShortName(String shortName);

    @Modifying
    @Query("DELETE FROM MarginRatesEntity AS mre WHERE mre.currency IN (SELECT ce FROM CurrencyEntity AS ce WHERE ce.shortName = ?1)")
    void deleteAllByShortName(String shortName);

}
