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
    private Double bid;

    @NotNull
    private Double ask;

    @NotNull
    @JsonFormat(pattern = Strings.DATE_FORMAT_YYYYMMDD_HHMISS)
    private Date timestamp;

    @NotNull
    private Integer currencyId;

    public void setId( Integer id ) {
        this.id = id ;
    }

    public Integer getId() {
        return this.id;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
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
}
