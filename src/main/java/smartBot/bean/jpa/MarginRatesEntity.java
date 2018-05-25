package smartBot.bean.jpa;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
    private Double maintenanceRate;

    @Column(name="volscanmaintenancerate")
    private Double volScanMaintenanceRate;

    @ManyToOne
    @JoinColumn(name="currency_id", nullable=false)
    private CurrencyEntity currency;

    @Column(name="startdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

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

    public Double getMaintenanceRate() {
        return maintenanceRate;
    }

    public void setMaintenanceRate(Double maintenanceRate) {
        this.maintenanceRate = maintenanceRate;
    }

    public Double getVolScanMaintenanceRate() {
        return volScanMaintenanceRate;
    }

    public void setVolScanMaintenanceRate(Double volScanMaintenanceRate) {
        this.volScanMaintenanceRate = volScanMaintenanceRate;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
