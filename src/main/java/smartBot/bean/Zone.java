package smartBot.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Zone implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer id;

    @Size(max = 255)
    private String name;

    private Scope scope;

    private ZoneLevel level;

    private Double price;

    private DateTime timestamp;

    private Double priceCalc;

    private Double priceCalcShift;

    private Double priceCalcOrderDetectionZone;

    private Double priceOrder;

    private Double priceStopLoss;

    private Double priceTakeProfit;

    private Double priceBreakEvenProfit;

    private Double priceTrailProfit;

    private Double priceATR;

    private Integer tradeCount;

    private Boolean activated;

    private Boolean touched;

    private Integer floor;

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

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public ZoneLevel getLevel() {
        return level;
    }

    public void setLevel(ZoneLevel level) {
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

    public Double getPriceATR() {
        return priceATR;
    }

    public void setPriceATR(Double priceATR) {
        this.priceATR = priceATR;
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

    public Boolean getTouched() {
        return touched;
    }

    public void setTouched(Boolean touched) {
        this.touched = touched;
    }

    @Override
    public String toString() {
        return "Zone{" +
                " scope=" + scope +
                " level=" + level +
                " floor=" + floor +
                "}";
    }

    @Override
    public int compareTo(Object o) {
        return this.level.getK().compareTo(((Zone)o).getLevel().getK());
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getFloor() {
        return floor;
    }
}
