package smartBot.bean.jpa;

import org.springframework.context.annotation.Lazy;
import smartBot.bean.Currency;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="currency", schema="public" )
@NamedQueries({
    @NamedQuery(name="CurrencyEntity.countAll", query="SELECT COUNT(x) FROM CurrencyEntity x")
})
public class CurrencyEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="name", length=255)
    private String name;

    @Column(name="shortname", length=255)
    private String shortName;

    @Lazy
    @OneToMany(mappedBy="currency", targetEntity=CurrencyEntity.class)
    private List<Currency> currency;

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public CurrencyEntity() {
        super();
    }

    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE FIELD
    //----------------------------------------------------------------------
    public void setId( Integer id ) {
        this.id = id ;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<Currency> getCurrency() {
        return currency;
    }

    public void setCurrency(List<Currency> currency) {
        this.currency = currency;
    }
}
