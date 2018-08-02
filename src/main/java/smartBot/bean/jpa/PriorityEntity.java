package smartBot.bean.jpa;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="priority", schema="public" )
@NamedQueries({
        @NamedQuery(name="PriorityEntity.countAll", query="SELECT COUNT(x) FROM PriorityEntity x")
})
public class PriorityEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="type", nullable=false)
    private PriorityTypeEntity type; // NULL - unknown; 1 - Buy; -1 - Sell;

    @ManyToOne
    @JoinColumn(name="subtype")
    private PrioritySubTypeEntity subtype; // NULL - unknown; 1 - Local; 2 - Global

    @Column(name="start_date", nullable=false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime startDate;

    @ManyToOne
    @JoinColumn(name="currency_id", nullable=false)
    private CurrencyEntity currency;

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public PriorityEntity() {
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

    public PriorityTypeEntity getType() {
        return type;
    }

    public void setType(PriorityTypeEntity type) {
        this.type = type;
    }

    public PrioritySubTypeEntity getSubtype() {
        return subtype;
    }

    public void setSubtype(PrioritySubTypeEntity subtype) {
        this.subtype = subtype;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }
}
