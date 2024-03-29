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

    @Column(name="type")
    private Integer type; // 1 - Buy; -1 - Sell

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy="type")
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
