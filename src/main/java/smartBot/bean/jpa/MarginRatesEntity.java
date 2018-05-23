package smartBot.bean.jpa;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="margin_rates", schema="public" )
@NamedQueries({
        @NamedQuery(name="MarginRatesEntity.countAll", query="SELECT COUNT(x) FROM MarginRatesEntity x")
})
public class MarginRatesEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="exchange")
    private String exchange;

    @Column(name="sector")
    private String sector;

    @Column(name="name")
    private String name;

    @Column(name="filepath")
    private String filePath;

    @Column(name="clearingcode")
    private String clearingCode;

    @Column(name="clearingorg")
    private String clearingOrg;

    @Column(name="productfamily")
    private String productFamily;

    @Column(name="startperiod")
    private String startPeriod;

    @Column(name="endperiod")
    private String endPeriod;

    @Column(name="maintenancerate")
    private String maintenanceRate;

    @Column(name="volscanmaintenancerate")
    private String volScanMaintenanceRate;

    @Column(name="currency")
    private String currency;

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public MarginRatesEntity() {
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
