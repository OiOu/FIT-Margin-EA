package smartBot.bean.jpa;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="currency_rates", schema="public" )
@NamedQueries({
        @NamedQuery(name="CurrencyRatesEntity.countAll", query="SELECT COUNT(x) FROM CurrencyRatesEntity x")
})
public class CurrencyRatesEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="high")
    private Double high;

    @Column(name="low")
    private Double low;

    @Column(name="open")
    private Double open;

    @Column(name="close")
    private Double close;

    @Column(name="volume")
    private Integer volume;

    @Column(name="point_pips")
    private Double pointPips;

    @Column(name="point_price")
    private Double pointPrice;

    @Column(name="timestamp")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime timestamp;

    @ManyToOne
    @JoinColumn(name="currency_id", nullable=false)
    private CurrencyEntity currency;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="scope_id", nullable=false)
    private ScopeEntity scope;
    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public CurrencyRatesEntity() {
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

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public Double getPointPips() {
        return pointPips;
    }

    public void setPointPips(Double pointPips) {
        this.pointPips = pointPips;
    }

    public Double getPointPrice() {
        return pointPrice;
    }

    public void setPointPrice(Double pointPrice) {
        this.pointPrice = pointPrice;
    }

    public ScopeEntity getScope() {
        return scope;
    }

    public void setScope(ScopeEntity scope) {
        this.scope = scope;
    }
}
