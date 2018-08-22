package smartBot.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderSettings implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer id;

    @NotNull
    private Currency currency;

    private Integer slSize;

    private Integer riskProfitMin;

    private Integer trail;

    private Integer breakEven;

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public OrderSettings() {
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getSlSize() {
        return slSize;
    }

    public void setSlSize(Integer slSize) {
        this.slSize = slSize;
    }

    public Integer getRiskProfitMin() {
        return riskProfitMin;
    }

    public void setRiskProfitMin(Integer riskProfitMin) {
        this.riskProfitMin = riskProfitMin;
    }

    public Integer getTrail() {
        return trail;
    }

    public void setTrail(Integer trail) {
        this.trail = trail;
    }

    public Integer getBreakEven() {
        return breakEven;
    }

    public void setBreakEven(Integer breakEven) {
        this.breakEven = breakEven;
    }
}
