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

    private Integer priorityType; // 1 - Buy; -1 - Sell

    private Integer prioritySubType; // 1 - Local; 2 - Global

    private Boolean tradeAllowed; // true - order can be opened on this level; false - can't be

    private Integer orderAssignmentShift;

    private Boolean enable; // true - order can be opened on this level; false - can't be

    private Integer stopLossSize;

    private Boolean dynamicStopLoss;

    private Integer riskProfitMin;

    private Integer trail;

    private Integer breakEven;

    private Double takeProfitPercent;

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

    public Boolean getTradeAllowed() {
        return tradeAllowed;
    }

    public void setTradeAllowed(Boolean tradeAllowed) {
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

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getDynamicStopLoss() {
        return dynamicStopLoss;
    }

    public void setDynamicStopLoss(Boolean dynamicStopLoss) {
        this.dynamicStopLoss = dynamicStopLoss;
    }

    public Integer getStopLossSize() {
        return stopLossSize;
    }

    public void setStopLossSize(Integer stopLossSize) {
        this.stopLossSize = stopLossSize;
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
