package smartBot.bean.jpa;

import org.springframework.format.annotation.DateTimeFormat;
import smartBot.defines.Strings;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

;

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

    @Column(name="bid")
    private Double bid;

    @Column(name="ask")
    private Double ask;

    @Column(name="timestamp")
    @DateTimeFormat(pattern = Strings.DATE_FORMAT_YYYYMMDD_HHMISS)
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name="currency_id", nullable=false)
    private CurrencyEntity currency;

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

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
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
}
