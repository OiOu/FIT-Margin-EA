package smartBot.bean.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="zone_level", schema="public" )
@NamedQueries({
    @NamedQuery(name="ZoneLevelEntity.countAll", query="SELECT COUNT(x) FROM ZoneLevelEntity x")
})
public class ZoneLevelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="k")
    private String k;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy="level", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ZoneInfoEntity> zonesInfo;

    @Column(name="local_priority")
    private Boolean localPriority;

    @Column(name="global_priority")
    private Boolean globalPriority;

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public ZoneLevelEntity() {
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

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ZoneInfoEntity> getZonesInfo() {
        return zonesInfo;
    }

    public void setZonesInfo(List<ZoneInfoEntity> zonesInfo) {
        this.zonesInfo = zonesInfo;
    }

    public Boolean getLocalPriority() {
        return localPriority;
    }

    public void setLocalPriority(Boolean localPriority) {
        this.localPriority = localPriority;
    }

    public Boolean getGlobalPriority() {
        return globalPriority;
    }

    public void setGlobalPriority(Boolean globalPriority) {
        this.globalPriority = globalPriority;
    }
}
