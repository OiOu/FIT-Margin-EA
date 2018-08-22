package smartBot.bean.jpa;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="order_settings", schema="public" )
@NamedQueries({
    @NamedQuery(name="OrderSettingsEntity.countAll", query="SELECT COUNT(x) FROM OrderSettingsEntity x")
})
public class OrderSettingsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="currency_id", nullable=false)
    private CurrencyEntity currency;

    @Column(name="sl_size")
    private Integer slSize;

    @Column(name="risk_profit_min")
    private Integer riskProfitMin;

    @Column(name="trail")
    private Integer trail;

    @Column(name="break_even")
    private Integer breakEven;

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public OrderSettingsEntity() {
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

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public Integer getSlSize() {
        return slSize;
    }

    public void setSlSize(Integer slSize) {
        this.slSize = slSize;
    }

    public Integer getRiskProfitMin() {
        return riskProfitMin;
    }

    public void setRiskProfitMin(Integer riskProfitMin) {
        this.riskProfitMin = riskProfitMin;
    }

    public Integer getTrail() {
        return trail;
    }

    public void setTrail(Integer trail) {
        this.trail = trail;
    }

    public Integer getBreakEven() {
        return breakEven;
    }

    public void setBreakEven(Integer breakEven) {
        this.breakEven = breakEven;
    }
}
