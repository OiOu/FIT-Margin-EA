package smartBot.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Size;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarginRates implements Serializable {

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

    private String maintenanceRate;

    private String volScanMaintenanceRate;

    private String currency; // shortname

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

    public String getMaintenanceRate() {
        return maintenanceRate;
    }

    public void setMaintenanceRate(String maintenanceRate) {
        this.maintenanceRate = maintenanceRate;
    }

    public String getVolScanMaintenanceRate() {
        return volScanMaintenanceRate;
    }

    public void setVolScanMaintenanceRate(String volScanMaintenanceRate) {
        this.volScanMaintenanceRate = volScanMaintenanceRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
