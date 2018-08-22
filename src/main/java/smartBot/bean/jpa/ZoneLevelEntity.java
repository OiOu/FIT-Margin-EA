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

    @Column(name="height")
    private Integer height;

    @Column(name="priority_distance")
    private Integer priorityDistance;

    @OneToMany(mappedBy="level", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ZoneEntity> zonesInfo;

    @Column(name="priority_type_id")
    private Integer priorityType; // 1 - Buy; -1 - Sell

    @Column(name="priority_subtype_id")
    private Integer prioritySubType; // 1 - Local; 2 - Global

    @Column(name="trade_allowed")
    private boolean tradeAllowed; // true - order can be opened on this level; false - can't be

    @Column(name="order_assignment_shift")
    private Integer orderAssignmentShift;

    @Column(name="enable")
    private boolean enable; // true - order can be opened on this level; false - can't be

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

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getPriorityDistance() {
        return priorityDistance;
    }

    public void setPriorityDistance(Integer priorityDistance) {
        this.priorityDistance = priorityDistance;
    }

    public List<ZoneEntity> getZonesInfo() {
        return zonesInfo;
    }

    public void setZonesInfo(List<ZoneEntity> zonesInfo) {
        this.zonesInfo = zonesInfo;
    }

    public Integer getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(Integer priorityType) {
        this.priorityType = priorityType;
    }

    public Integer getPrioritySubType() {
        return prioritySubType;
    }

    public void setPrioritySubType(Integer prioritySubType) {
        this.prioritySubType = prioritySubType;
    }

    public boolean isTradeAllowed() {
        return tradeAllowed;
    }

    public void setTradeAllowed(boolean tradeAllowed) {
        this.tradeAllowed = tradeAllowed;
    }

    public Integer getOrderAssignmentShift() {
        return orderAssignmentShift;
    }

    public void setOrderAssignmentShift(Integer orderAssignmentShift) {
        this.orderAssignmentShift = orderAssignmentShift;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
