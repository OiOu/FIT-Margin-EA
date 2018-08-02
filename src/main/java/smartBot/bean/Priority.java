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

    private PriorityType type;

    private PrioritySubType subtype;

    private DateTime startDate;

    private DateTime endDate;

    private Currency currency;

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

    public PriorityType getType() {
        return type;
    }

    public void setType(PriorityType type) {
        this.type = type;
    }

    public PrioritySubType getSubtype() {
        return subtype;
    }

    public void setSubtype(PrioritySubType subtype) {
        this.subtype = subtype;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
