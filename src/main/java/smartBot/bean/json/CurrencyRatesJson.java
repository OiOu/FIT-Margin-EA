package smartBot.bean.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import smartBot.defines.Strings;
import smartBot.utils.DoubleUtils;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRatesJson implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer period;

    private Integer priority;

    private Double high;

    private Double low;

    private Double open;

    private Double close;

    private Integer volume;

    @JsonFormat(pattern = Strings.DATE_FORMAT_YYYYMMDD_HHMISS)
    private DateTime timestamp;

    private String currency;

    private Double pointPrice;

    private Double pointPips;

    private Double atrPriceFromMonthLow;

    private Double atrPriceFromMonthHigh;

    private boolean isHistory;

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = DoubleUtils.round(high, 5);
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = DoubleUtils.round(low, 5);
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = DoubleUtils.round(open, 5);
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = DoubleUtils.round(close, 5);
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp.toDateTime(DateTimeZone.UTC);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
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

    public Double getAtrPriceFromMonthLow() {
        return atrPriceFromMonthLow;
    }

    public void setAtrPriceFromMonthLow(Double atrPriceFromMonthLow) {
        this.atrPriceFromMonthLow = atrPriceFromMonthLow;
    }

    public Double getAtrPriceFromMonthHigh() {
        return atrPriceFromMonthHigh;
    }

    public void setAtrPriceFromMonthHigh(Double atrPriceFromMonthHigh) {
        this.atrPriceFromMonthHigh = atrPriceFromMonthHigh;
    }

    public boolean isHistory() {
        return isHistory;
    }

    public void setHistory(boolean history) {
        isHistory = history;
    }
}
