package smartBot.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import smartBot.defines.Strings;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRates implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer id;

    @NotNull
    private Integer period;

    @NotNull
    private Double high;

    @NotNull
    private Double low;

    @NotNull
    private Double open;

    private Double close;

    @NotNull
    private Integer volume;

    private Double pips;

    @NotNull
    @JsonFormat(pattern = Strings.DATE_FORMAT_YYYYMMDD_HHMISS)
    private Date timestamp;

    @NotNull
    private Integer currencyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
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

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Double getPips() {
        return pips;
    }

    public void setPips(Double pips) {
        this.pips = pips;
    }
}
