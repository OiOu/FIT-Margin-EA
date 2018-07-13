package smartBot.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Priority implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer id;

    private Integer type;

    private DateTime startDate;


    /*@Column(name="usa_open_price")
    private Double usaOpenPrice;

    @Column(name="usa_close_price")
    private Double usaClosePrice;

    @Column(name="usa_open_date")
    private DateTime usaOpenDate;

    @Column(name="usa_close_date")
    private DateTime usaCloseDate;

    @Column(name="eur_open_price")
    private Double eurOpenPrice;

    @Column(name="eur_close_price")
    private Double eurClosePrice;

    @Column(name="eur_open_date")
    private DateTime eurOpenDate;

    @Column(name="eur_close_date")
    private DateTime eurCloseDate;*/

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public Priority() {
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

/*public Double getUsaOpenPrice() {
        return usaOpenPrice;
    }

    public void setUsaOpenPrice(Double usaOpenPrice) {
        this.usaOpenPrice = usaOpenPrice;
    }

    public Double getUsaClosePrice() {
        return usaClosePrice;
    }

    public void setUsaClosePrice(Double usaClosePrice) {
        this.usaClosePrice = usaClosePrice;
    }

    public DateTime getUsaOpenDate() {
        return usaOpenDate;
    }

    public void setUsaOpenDate(DateTime usaOpenDate) {
        this.usaOpenDate = usaOpenDate;
    }

    public DateTime getUsaCloseDate() {
        return usaCloseDate;
    }

    public void setUsaCloseDate(DateTime usaCloseDate) {
        this.usaCloseDate = usaCloseDate;
    }

    public Double getEurOpenPrice() {
        return eurOpenPrice;
    }

    public void setEurOpenPrice(Double eurOpenPrice) {
        this.eurOpenPrice = eurOpenPrice;
    }

    public Double getEurClosePrice() {
        return eurClosePrice;
    }

    public void setEurClosePrice(Double eurClosePrice) {
        this.eurClosePrice = eurClosePrice;
    }

    public DateTime getEurOpenDate() {
        return eurOpenDate;
    }

    public void setEurOpenDate(DateTime eurOpenDate) {
        this.eurOpenDate = eurOpenDate;
    }

    public DateTime getEurCloseDate() {
        return eurCloseDate;
    }

    public void setEurCloseDate(DateTime eurCloseDate) {
        this.eurCloseDate = eurCloseDate;
    }*/
}
