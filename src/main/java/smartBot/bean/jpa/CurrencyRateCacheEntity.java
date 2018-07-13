package smartBot.bean.jpa;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="currency_rates_cache", schema="public" )
@NamedQueries({
        @NamedQuery(name="CurrencyRateCacheEntity.countAll", query="SELECT COUNT(x) FROM CurrencyRateCacheEntity x")
})
public class CurrencyRateCacheEntity  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="type")
    private Integer type;

    @ManyToOne
    @JoinColumn(name="currency_id", nullable=false)
    private CurrencyEntity currency;

    @ManyToOne
    @JoinColumn(name="currency_rate_id", nullable=false)
    private CurrencyRatesEntity currencyRates;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public CurrencyRatesEntity getCurrencyRates() {
        return currencyRates;
    }

    public void setCurrencyRates(CurrencyRatesEntity currencyRates) {
        this.currencyRates = currencyRates;
    }
}
