package smartBot.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import smartBot.defines.Strings;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Scope implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;

    public static final Integer BUILD_FROM_HIGH = 1;
    public static final Integer BUILD_FROM_LOW = -1;

    @NotNull
    private Integer id;

    @Size( max = 255 )
    private String name;

    @NotNull
    @JsonFormat(pattern = Strings.DATE_FORMAT_YYYYMMDD_HHMISS)
    private Date timestampFrom;

    @NotNull
    private Currency currency;

    @NotNull
    private Integer type;

    private List<Zone> zones = new ArrayList<>();

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

    public Date getTimestampFrom() {
        return timestampFrom;
    }

    public void setTimestampFrom(Date timestampFrom) {
        this.timestampFrom = timestampFrom;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    @Override
    public String toString() {
        return "Scope{" +
                timestampFrom +
                ", " + currency.getShortName() +
                ", " + type +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        /* For Desc order*/
        return ((Scope)o).getTimestampFrom().compareTo(this.timestampFrom);
    }
}
