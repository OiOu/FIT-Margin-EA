package smartBot.bean.jpa;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="priority_subtype", schema="public" )
@NamedQueries({
        @NamedQuery(name="PrioritySubTypeEntity.countAll", query="SELECT COUNT(x) FROM PrioritySubTypeEntity x")
})
public class PrioritySubTypeEntity {

    @Id
    @Column(name="id", nullable=false)
    private Integer id;

    @Column(name="subtype")
    private Integer subtype; // 1 - Local; 2 - Global

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy="subtype")
    private List<PriorityEntity> priorities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubtype() {
        return subtype;
    }

    public void setSubtype(Integer subtype) {
        this.subtype = subtype;
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
