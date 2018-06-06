package smartBot.bean.jpa;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="zone_info", schema="public" )
@NamedQueries({
    @NamedQuery(name="ZoneInfoEntity.countAll", query="SELECT COUNT(x) FROM ZoneInfoEntity x")
})
public class ZoneInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name="scope_id", nullable=false)
    private ScopeZonesEntity scope;

    @ManyToOne
    @JoinColumn(name="level_id", nullable=false)
    private ZoneLevelEntity level;

    @Column(name="price")
    private Double price;

    @Column(name="timestamp")
    private DateTime timestamp;

    @Column(name="price_calc")
    private Double priceCalc;

    @Column(name="price_calc_shift")
    private Double priceCalcShift;

    @Column(name="trade_count")
    private Integer tradeCount;

    @Column(name="activated")
    private Boolean activated;

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public ZoneInfoEntity() {
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

    public ScopeZonesEntity getScope() {
        return scope;
    }

    public void setScope(ScopeZonesEntity scope) {
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

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
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
