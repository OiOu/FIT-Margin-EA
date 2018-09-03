package smartBot.bean.jpa;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import smartBot.utils.DoubleUtils;

import javax.persistence.*;
import java.io.Serializable;

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
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime timestamp;

    @Column(name="price_calc")
    private Double priceCalc;

    @Column(name="price_calc_shift")
    private Double priceCalcShift;

    @Column(name="price_calc_order_detection_zone")
    private Double priceCalcOrderDetectionZone;

    @Column(name="price_order")
    private Double priceOrder;

    @Column(name="price_stop_loss")
    private Double priceStopLoss;

    @Column(name="price_take_profit")
    private Double priceTakeProfit;

    @Column(name="price_break_even_profit")
    private Double priceBreakEvenProfit;

    @Column(name="price_trail_profit")
    private Double priceTrailProfit;

    @Column(name="trade_count")
    private Integer tradeCount;

    @Column(name="activated")
    private Boolean activated;

    @Column(name="touched")
    private Boolean touched;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="scope_id", nullable=false)
    private ScopeEntity scope;

    @ManyToOne
    @JoinColumn(name="level_id", nullable=false)
    private ZoneLevelEntity level;

    @Column(name="floor")
    private Integer floor;
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
        this.priceCalc = DoubleUtils.round(priceCalc, 5);
    }

    public Double getPriceCalcShift() {
        return priceCalcShift;
    }

    public void setPriceCalcShift(Double priceCalcShift) {
        this.priceCalcShift = DoubleUtils.round(priceCalcShift, 5);
    }

    public Double getPriceCalcOrderDetectionZone() {
        return priceCalcOrderDetectionZone;
    }

    public void setPriceCalcOrderDetectionZone(Double priceCalcOrderDetectionZone) {
        this.priceCalcOrderDetectionZone = priceCalcOrderDetectionZone;
    }

    public Double getPriceOrder() {
        return priceOrder;
    }

    public void setPriceOrder(Double priceOrder) {
        this.priceOrder = priceOrder;
    }

    public Double getPriceStopLoss() {
        return priceStopLoss;
    }

    public void setPriceStopLoss(Double priceStopLoss) {
        this.priceStopLoss = priceStopLoss;
    }

    public Double getPriceTakeProfit() {
        return priceTakeProfit;
    }

    public void setPriceTakeProfit(Double priceTakeProfit) {
        this.priceTakeProfit = priceTakeProfit;
    }

    public Integer getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(Integer tradeCount) {
        this.tradeCount = tradeCount;
    }

    public Double getPriceBreakEvenProfit() {
        return priceBreakEvenProfit;
    }

    public void setPriceBreakEvenProfit(Double priceBreakEvenProfit) {
        this.priceBreakEvenProfit = priceBreakEvenProfit;
    }

    public Double getPriceTrailProfit() {
        return priceTrailProfit;
    }

    public void setPriceTrailProfit(Double priceTrailProfit) {
        this.priceTrailProfit = priceTrailProfit;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Boolean getTouched() {
        return touched;
    }

    public void setTouched(Boolean touched) {
        this.touched = touched;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }
}
