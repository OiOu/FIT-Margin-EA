package smartBot.bean.jpa;

import org.springframework.format.annotation.DateTimeFormat;
import smartBot.defines.Strings;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    @Column(name="timestamp")
    @DateTimeFormat(pattern = Strings.DATE_FORMAT_YYYYMMDD_HHMISS)
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name="currency_id", nullable=false)
    private CurrencyEntity currency;

    @ManyToOne
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public ScopeEntity getScope() {
        return scope;
    }

    public void setScope(ScopeEntity scope) {
        this.scope = scope;
    }
}
