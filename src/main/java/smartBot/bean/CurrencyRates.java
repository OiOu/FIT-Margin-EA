package smartBot.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRates implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer id;

    @NotNull
    private Double high;

    @NotNull
    private Double low;

    @NotNull
    private Double open;

    private Double close;

    @NotNull
    private Integer volume;

    private Double pointPips;

    private Double pointPrice;

    @NotNull
    private Date timestamp;

    @NotNull
    private Currency currency;

    @NotNull
    private Scope scope;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
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
}
