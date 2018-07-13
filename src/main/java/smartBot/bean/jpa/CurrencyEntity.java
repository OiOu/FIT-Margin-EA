package smartBot.bean.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="currency", schema="public" )
@NamedQueries({
    @NamedQuery(name="CurrencyEntity.countAll", query="SELECT COUNT(x) FROM CurrencyEntity x")
})
public class CurrencyEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="name", length=255)
    private String name;

    @Column(name="short_name", length=255)
    private String shortName;

    @Column(name="clearing_code", length=255)
    private String clearingCode;

    @Column(name="futures_code", length=255)
    private String futuresCode;

    @Column(name="inverted")
    private boolean inverted;

    @OneToMany(mappedBy="currency", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CurrencyRatesEntity> rates;

    @OneToMany(mappedBy="currency", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MarginRatesEntity> margins;

    @OneToMany(mappedBy="currency", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ScopeEntity> scopes;

    /*@OneToMany(mappedBy="currency", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CurrencyRateCacheEntity> cache;*/
    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public CurrencyEntity() {
        super();
    }

    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE FIELD
    //----------------------------------------------------------------------
    public void setId( Integer id ) {
        this.id = id ;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getClearingCode() {
        return clearingCode;
    }

    public void setClearingCode(String clearingCode) {
        this.clearingCode = clearingCode;
    }

    public String getFuturesCode() {
        return futuresCode;
    }

    public void setFuturesCode(String futuresCode) {
        this.futuresCode = futuresCode;
    }

    public boolean getInverted() {
        return inverted;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    public List<CurrencyRatesEntity> getRates() {
        return rates;
    }

    public void setRates(List<CurrencyRatesEntity> rates) {
        this.rates = rates;
    }

    public List<MarginRatesEntity> getMargins() {
        return margins;
    }

    public void setMargins(List<MarginRatesEntity> margins) {
        this.margins = margins;
    }

    public List<ScopeEntity> getScopes() {
        return scopes;
    }

    public void setScopes(List<ScopeEntity> scopes) {
        this.scopes = scopes;
    }

    /*public List<CurrencyRateCacheEntity> getCache() {
        return cache;
    }

    public void setCache(List<CurrencyRateCacheEntity> cache) {
        this.cache = cache;
    }*/
}
