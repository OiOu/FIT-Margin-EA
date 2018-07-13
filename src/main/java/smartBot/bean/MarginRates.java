package smartBot.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarginRates implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @Size( max = 255 )
    private String exchange;

    @Size( max = 255 )
    private String sector;

    @Size( max = 255 )
    private String name;

    @Size( max = 255 )
    private String filePath;

    @Size( max = 255 )
    private String clearingCode;

    @Size( max = 255 )
    private String clearingOrg;

    @Size( max = 255 )
    private String productFamily;

    private String startPeriod;

    private String endPeriod;

    private Double maintenanceRate;

    private String volScanMaintenanceRate;

    private Date startDate = new Date();

    private Double pricePerContract;

    private Double futurePoint;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getClearingCode() {
        return clearingCode;
    }

    public void setClearingCode(String clearingCode) {
        this.clearingCode = clearingCode;
    }

    public String getClearingOrg() {
        return clearingOrg;
    }

    public void setClearingOrg(String clearingOrg) {
        this.clearingOrg = clearingOrg;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public String getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(String startPeriod) {
        this.startPeriod = startPeriod;
    }

    public String getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    public Double getMaintenanceRate() {
        return maintenanceRate;
    }

    public void setMaintenanceRate(String maintenanceRate) {
        if (maintenanceRate == null || maintenanceRate.contains("%")) {
            this.maintenanceRate = Double.valueOf(-1);
        } else {
            if (StringUtils.isNotEmpty(maintenanceRate) && maintenanceRate.contains(" ")) {
                maintenanceRate = maintenanceRate.substring(0, maintenanceRate.indexOf(" "));
            }
            if (StringUtils.isNotEmpty(maintenanceRate)) {
                maintenanceRate = maintenanceRate.replace(",", "");
                this.maintenanceRate = Double.valueOf(maintenanceRate);
            } else {
                this.maintenanceRate = Double.valueOf(-1);
            }
        }
    }

    public String getVolScanMaintenanceRate() {
        return volScanMaintenanceRate;
    }

    public void setVolScanMaintenanceRate(String volScanMaintenanceRate) {
        this.volScanMaintenanceRate = volScanMaintenanceRate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    @Override
    public int compareTo(Object o) {
        /* For Desc order*/
        return ((MarginRates)o).getStartDate().compareTo(this.startDate);
    }
}
