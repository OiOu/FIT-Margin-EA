package smartBot.data.repository.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import smartBot.bean.jpa.CurrencyRatesEntity;

import java.util.List;

public interface CurrencyRatesJpaRepository extends CrudRepository<CurrencyRatesEntity, Integer> {

    @Query("SELECT cre FROM CurrencyRatesEntity cre")
    List<CurrencyRatesEntity> findAll();

    @Query("SELECT cre FROM CurrencyRatesEntity cre JOIN cre.currency c WHERE c.shortName = ?1")
    List<CurrencyRatesEntity> findAllByCurrencyShortName(String shortName);

    @Query("SELECT cre FROM CurrencyRatesEntity cre JOIN cre.currency c WHERE c.id = $1")
    List<CurrencyRatesEntity> findAllByCurrencyId(Integer currencyId);

    @Query("SELECT cre FROM CurrencyRatesEntity cre WHERE cre.id = ?1 ")
    CurrencyRatesEntity getById(Integer id);

    @Query("SELECT cre FROM CurrencyRatesEntity cre WHERE cre.id IN (?1) ")
    List<CurrencyRatesEntity> getByIds(List<Integer> ids);

    @Modifying
    @Query("DELETE FROM CurrencyRatesEntity AS cre WHERE cre.currency.shortName = ?1")
    void deleteAllByShortName(String shortName);
}
