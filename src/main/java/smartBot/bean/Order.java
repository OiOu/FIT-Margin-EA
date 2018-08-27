package smartBot.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    private Integer ticket;

    @NotNull
    private Integer type;

    @NotNull
    private Integer subtype;

    @NotNull
    private Currency currency;

    @NotNull
    private ZoneLevel level;

    @NotNull
    private DateTime openTimestamp;

    private DateTime closeTimestamp;

    @NotNull
    private Double price;

    @NotNull
    private Double priceStopLoss;

    @NotNull
    private Double priceTakeProfit;

    private Double priceBreakEvenProfit;

    private Double priceTrailProfit;

    private Integer closeReason;

    private Boolean breakEvenActivated;

    private Integer points;

    private Boolean activated;
    private Boolean trailStopActivated;

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public Order() {
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

    public Integer getTicket() {
        return ticket;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSubtype() {
        return subtype;
    }

    public void setSubtype(Integer subtype) {
        this.subtype = subtype;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public ZoneLevel getLevel() {
        return level;
    }

    public void setLevel(ZoneLevel level) {
        this.level = level;
    }

    public DateTime getOpenTimestamp() {
        return openTimestamp;
    }

    public void setOpenTimestamp(DateTime openTimestamp) {
        this.openTimestamp = openTimestamp;
    }

    public DateTime getCloseTimestamp() {
        return closeTimestamp;
    }

    public void setCloseTimestamp(DateTime closeTimestamp) {
        this.closeTimestamp = closeTimestamp;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Integer getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(Integer closeReason) {
        this.closeReason = closeReason;
    }

    public Boolean getBreakEvenActivated() {
        return this.breakEvenActivated;
    }

    public void setBreakEvenActivated(Boolean breakEvenActivated) {
        this.breakEvenActivated = breakEvenActivated;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Boolean getTrailStopActivated() {
        return trailStopActivated;
    }

    public void setTrailStopActivated(Boolean trailStopActivated) {
        this.trailStopActivated = trailStopActivated;
    }
}
