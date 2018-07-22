package smartBot.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

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

    private Date timestamp;

    private Double priceCalc;

    private Double priceCalcShift;

    private Integer tradeCount;

    private Boolean activated;

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

    @Override
    public String toString() {
        return "Zone{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", scopeId=" + scope +
                ", level=" + level +
                ", price=" + price +
                ", timestamp=" + timestamp +
                ", priceCalc=" + priceCalc +
                ", priceCalcShift=" + priceCalcShift +
                ", tradeCount=" + tradeCount +
                ", activated=" + activated +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return this.level.getK().compareTo(((Zone)o).getLevel().getK());
    }
}
