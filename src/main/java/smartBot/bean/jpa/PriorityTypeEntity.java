package smartBot.bean.jpa;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="priority_type", schema="public" )
@NamedQueries({
        @NamedQuery(name="PriorityTypeEntity.countAll", query="SELECT COUNT(x) FROM PriorityTypeEntity x")
})
public class PriorityTypeEntity {

    @Id
    @Column(name="id", nullable=false)
    private Integer id;

    @JoinColumn(name="type")
    private Integer type; // 1 - Buy; -1 - Sell

    @JoinColumn(name="subtype")
    private Integer subType; // 1 - Local; 2 - Global

    @JoinColumn(name="name")
    private String name;

    @OneToMany(mappedBy="type", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PriorityEntity> priorities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PriorityEntity> getPriorities() {
        return priorities;
    }

    public void setPriorities(List<PriorityEntity> priorities) {
        this.priorities = priorities;
    }
}
