package domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author SAMUEL
 */
@Entity(name="ClassGroups")
@Table(name = "ClassGroups")
@NamedQueries({
    @NamedQuery(name="AllClassGroups",
                query="SELECT c FROM ClassGroups c"),
    @NamedQuery(name = "ClassGroupsOfSchoolYear", 
            query = "SELECT cg FROM ClassGroups cg WHERE cg.schoolYear = :sy "), 
    @NamedQuery(name = "ClassGroupWithName", 
            query = "SELECT cg FROM ClassGroups cg WHERE cg.groupName = :name "),
    @NamedQuery(name="ClassGroup.findById",query="SELECT cg FROM ClassGroups cg WHERE cg.groupId = :id")
    
}) 
public class ClassGroup implements Serializable {
    
    @Column(name = "Klasgroep")
    private String groupName;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GroupID")
    private int groupId;
    
    @OneToMany(mappedBy = "classGroup", cascade = CascadeType.REMOVE)
    private List<Student> students;
    
    
    @ManyToOne
    @JoinColumn(name = "schoolYear")
    private SchoolYear schoolYear;

    //CTOR
    public ClassGroup() {
    }

    public ClassGroup(String groupName) {
        this.groupName = groupName;
    }

    public ClassGroup(String groupName, SchoolYear schoolYear) {
        this.groupName = groupName;
        this.schoolYear = schoolYear;
    }

    //GET SET
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    
    @Override
    public String toString(){
        return this.groupName;
    }
    
}
