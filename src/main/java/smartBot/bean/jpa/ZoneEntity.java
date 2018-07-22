package smartBot.bean.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="zone", schema="public" )
@NamedQueries({
    @NamedQuery(name="ZoneEntity.countAll", query="SELECT COUNT(x) FROM ZoneEntity x")
})
public class ZoneEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="price")
    private Double price;

    @Column(name="timestamp")
    private Date timestamp;

    @Column(name="price_calc")
    private Double priceCalc;

    @Column(name="price_calc_shift")
    private Double priceCalcShift;

    @Column(name="trade_count")
    private Integer tradeCount;

    @Column(name="activated")
    private Boolean activated;

    @ManyToOne
    @JoinColumn(name="scope_id", nullable=false)
    private ScopeEntity scope;

    @ManyToOne
    @JoinColumn(name="level_id", nullable=false)
    private ZoneLevelEntity level;

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public ZoneEntity() {
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

    public ScopeEntity getScope() {
        return scope;
    }

    public void setScope(ScopeEntity scope) {
        this.scope = scope;
    }

    public ZoneLevelEntity getLevel() {
        return level;
    }

    public void setLevel(ZoneLevelEntity level) {
        this.level = level;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getPriceCalc() {
        return priceCalc;
    }

    public void setPriceCalc(Double priceCalc) {
        this.priceCalc = priceCalc;
    }

    public Double getPriceCalcShift() {
        return priceCalcShift;
    }

    public void setPriceCalcShift(Double priceCalcShift) {
        this.priceCalcShift = priceCalcShift;
    }

    public Integer getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(Integer tradeCount) {
        this.tradeCount = tradeCount;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }
}