package smartBot.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer id;

    @Size( min = 1, max = 255 )
    private String name;

    @NotNull
    @Size( max = 255 )
    private String shortName; // currency code: EUR, USD, CAD

    @NotNull
    @Size( max = 255 )
    private String clearingCode; // feature code

    @Size( max = 255 )
    private String futuresCode;

    @NotNull
    private boolean inverted;

    private Double pricePerContract;

    private Double futurePoint;

    private Integer k;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getClearingCode() {
        return clearingCode;
    }

    public void setClearingCode(String clearingCode) {
        this.clearingCode = clearingCode;
    }

    public String getFuturesCode() {
        return futuresCode;
    }

    public void setFuturesCode(String futuresCode) {
        this.futuresCode = futuresCode;
    }

    public boolean getInverted() {
        return inverted;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    public Double getPricePerContract() {
        return pricePerContract;
    }

    public void setPricePerContract(Double pricePerContract) {
        this.pricePerContract = pricePerContract;
    }

    public Double getFuturePoint() {
        return futurePoint;
    }

    public void setFuturePoint(Double futurePoint) {
        this.futurePoint = futurePoint;
    }

    public Integer getK() {
        return k;
    }

    public void setK(Integer k) {
        this.k = k;
    }
}
