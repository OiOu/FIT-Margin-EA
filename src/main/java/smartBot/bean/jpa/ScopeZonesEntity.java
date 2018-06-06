package smartBot.bean.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="scope_zone", schema="public" )
@NamedQueries({
    @NamedQuery(name="ScopeZonesEntity.countAll", query="SELECT COUNT(x) FROM ScopeZonesEntity x")
})
public class ScopeZonesEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name="currency_id", nullable=false)
    private CurrencyEntity currency;

    @OneToMany(mappedBy="scope", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ZoneInfoEntity> zonesData;

    @Column(name="status")
    private Integer status; // indicates what status has the scope: 1 - actual (by priority), 2 - inverse (inverse priority); 3 - closed (actual became close after priority change)

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public ScopeZonesEntity() {
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

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public List<ZoneInfoEntity> getZonesData() {
        return zonesData;
    }

    public void setZonesData(List<ZoneInfoEntity> zonesData) {
        this.zonesData = zonesData;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
