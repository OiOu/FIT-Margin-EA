package smartBot.bean.jpa;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="exchange")
    private String exchange;

    @Column(name="sector")
    private String sector;

    @Column(name="name")
    private String name;

    @Column(name="file_path")
    private String filePath;

    @Column(name="clearing_code", nullable=false)
    private String clearingCode;

    @Column(name="clearing_org")
    private String clearingOrg;

    @Column(name="product_family")
    private String productFamily;

    @Column(name="start_period")
    private String startPeriod;

    @Column(name="end_period")
    private String endPeriod;

    @Column(name="maintenance_rate")
    private Double maintenanceRate;

    @Column(name="vol_scan_maintenance_rate")
    private String volScanMaintenanceRate;

    @ManyToOne
    @JoinColumn(name="currency_id", nullable=false)
    private CurrencyEntity currency;

    @Column(name="start_date")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime startDate = new DateTime();

    @Column(name="end_date")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime endDate = new DateTime();

    @Column(name="price_per_contract")
    private Double pricePerContract;

    @Column(name="future_point")
    private Double futurePoint;

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

    public String getVolScanMaintenanceRate() {
        return volScanMaintenanceRate;
    }

    public void setVolScanMaintenanceRate(String volScanMaintenanceRate) {
        this.volScanMaintenanceRate = volScanMaintenanceRate;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
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
}
