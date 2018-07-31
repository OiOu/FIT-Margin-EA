package smartBot.bean.jpa;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="scope", schema="public" )
@NamedQueries({
    @NamedQuery(name="ScopeEntity.countAll", query="SELECT COUNT(x) FROM ScopeEntity x")
})
public class ScopeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="timestamp_from")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime timestampFrom;

    @Column(name="timestamp_to")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime timestampTo;

    @Column(name="type")
    private Integer type;

    @ManyToOne
    @JoinColumn(name="currency_id", nullable=false)
    private CurrencyEntity currency;

    @OneToMany(mappedBy="scope")
    private List<ZoneEntity> zones;

    @OneToMany(mappedBy="scope")
    private List<CurrencyRatesEntity> currencyRates;

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public ScopeEntity() {
        super();
    }

    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE FIELD
    //----------------------------------------------------------------------

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getTimestampFrom() {
        return timestampFrom;
    }

    public void setTimestampFrom(DateTime timestampFrom) {
        this.timestampFrom = timestampFrom;
    }

    public DateTime getTimestampTo() {
        return timestampTo;
    }

    public void setTimestampTo(DateTime timestampTo) {
        this.timestampTo = timestampTo;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public List<ZoneEntity> getZones() {
        return zones;
    }

    public void setZones(List<ZoneEntity> zones) {
        this.zones = zones;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<CurrencyRatesEntity> getCurrencyRates() {
        return currencyRates;
    }

    public void setCurrencyRates(List<CurrencyRatesEntity> currencyRates) {
        this.currencyRates = currencyRates;
    }
}
