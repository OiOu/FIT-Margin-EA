package smartBot.data.repository.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import smartBot.bean.jpa.CurrencyEntity;

import java.util.List;

public interface CurrencyJpaRepository extends CrudRepository<CurrencyEntity, Integer> {
    @Query("SELECT ce " +
                " FROM CurrencyEntity ce " +
                    " WHERE " +
                        " ce.id in (?1)")
    List<CurrencyEntity> findByIds(List<Integer> ids);

    @Query("SELECT ce " +
            " FROM CurrencyEntity ce " +
                " WHERE " +
                    " ce.id = ?1")
    CurrencyEntity getById(Integer id);

    @Query("SELECT ce " +
                " FROM CurrencyEntity ce " +
                    " WHERE " +
                        " ce.shortName in (?1)")
    List<CurrencyEntity> findByShortNames(List<String> shortNames);


    @Query("SELECT ce " +
                " FROM CurrencyEntity ce " +
                    " WHERE " +
                        " ce.shortName = ?1")
    CurrencyEntity findByShortName(String shortName);

    @Modifying
    @Query("DELETE FROM CurrencyEntity AS ce WHERE ce.shortName = ?1")
    void deleteAllByShortName(String shortName);

}
