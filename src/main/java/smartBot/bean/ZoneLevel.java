package smartBot.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZoneLevel implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer id;

    @NotNull
    private Double k;

    @NotNull
    private Integer height;

    private Integer priorityDistance;

    @Size( max = 255 )
    private String name;

    private Integer priorityType;

    private Integer prioritySubType;

    private boolean tradeAllowed;

    private Integer orderAssignmentShift;

    private Double takeProfitPercent;

    private boolean enable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getK() {
        return k;
    }

    public void setK(Double k) {
        this.k = k;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getPriorityDistance() {
        return priorityDistance;
    }

    public void setPriorityDistance(Integer priorityDistance) {
        this.priorityDistance = priorityDistance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(Integer priorityType) {
        this.priorityType = priorityType;
    }

    public Integer getPrioritySubType() {
        return prioritySubType;
    }

    public void setPrioritySubType(Integer prioritySubType) {
        this.prioritySubType = prioritySubType;
    }

    public boolean isTradeAllowed() {
        return tradeAllowed;
    }

    public void setTradeAllowed(boolean tradeAllowed) {
        this.tradeAllowed = tradeAllowed;
    }

    public Integer getOrderAssignmentShift() {
        return orderAssignmentShift;
    }

    public void setOrderAssignmentShift(Integer orderAssignmentShift) {
        this.orderAssignmentShift = orderAssignmentShift;
    }

    public Double getTakeProfitPercent() {
        return takeProfitPercent;
    }

    public void setTakeProfitPercent(Double takeProfitPercent) {
        this.takeProfitPercent = takeProfitPercent;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public int compareTo(Object o) {
         /* For Asc order*/
        return this.k.compareTo(((ZoneLevel)o).getK());
    }

    @Override
    public String toString() {
        return "ZoneLevel{" +
                k+
                '}';
    }
}
