package smartBot.bean.jpa;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="order", schema="public" )
@NamedQueries({
    @NamedQuery(name="OrderEntity.countAll", query="SELECT COUNT(x) FROM OrderEntity x")
})
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="ticket")
    private Integer ticket;

    @Column(name="type")
    private Integer type;

    @Column(name="subtype")
    private Integer subtype;

    @ManyToOne
    @JoinColumn(name="currency_id", nullable=false)
    private CurrencyEntity currency;

    @ManyToOne
    @JoinColumn(name="level_id", nullable=false)
    private ZoneLevelEntity level;

    @Column(name="open_timestamp")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime openTimestamp;

    @Column(name="price")
    private Double price;

    @Column(name="price_stop_loss")
    private Double priceStopLoss;

    @Column(name="price_take_profit")
    private Double priceTakeProfit;

    @Column(name="price_break_even_profit")
    private Double priceBreakEvenProfit;

    @Column(name="price_trail_profit")
    private Double priceTrailProfit;

    @Column(name="close_reason")
    private Integer closeReason;

    @Column(name="break_even_activated")
    private Boolean breakEvenActivated;

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public OrderEntity() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTicket() {
        return ticket;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSubtype() {
        return subtype;
    }

    public void setSubtype(Integer subtype) {
        this.subtype = subtype;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public ZoneLevelEntity getLevel() {
        return level;
    }

    public void setLevel(ZoneLevelEntity level) {
        this.level = level;
    }

    public DateTime getOpenTimestamp() {
        return openTimestamp;
    }

    public void setOpenTimestamp(DateTime openTimestamp) {
        this.openTimestamp = openTimestamp;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPriceStopLoss() {
        return priceStopLoss;
    }

    public void setPriceStopLoss(Double priceStopLoss) {
        this.priceStopLoss = priceStopLoss;
    }

    public Double getPriceTakeProfit() {
        return priceTakeProfit;
    }

    public void setPriceTakeProfit(Double priceTakeProfit) {
        this.priceTakeProfit = priceTakeProfit;
    }

    public Double getPriceBreakEvenProfit() {
        return priceBreakEvenProfit;
    }

    public void setPriceBreakEvenProfit(Double priceBreakEvenProfit) {
        this.priceBreakEvenProfit = priceBreakEvenProfit;
    }

    public Double getPriceTrailProfit() {
        return priceTrailProfit;
    }

    public void setPriceTrailProfit(Double priceTrailProfit) {
        this.priceTrailProfit = priceTrailProfit;
    }

    public Integer getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(Integer closeReason) {
        this.closeReason = closeReason;
    }

    public Boolean getBreakEvenActivated() {
        return breakEvenActivated;
    }

    public void setBreakEvenActivated(Boolean breakEvenActivated) {
        this.breakEvenActivated = breakEvenActivated;
    }
}
