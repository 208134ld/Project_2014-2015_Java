package domain;

import java.io.Serializable;
import java.util.List;
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

@Entity(name = "Grades")
@Table(name = "Grades")
@NamedQueries({
    @NamedQuery(name = "Grade.AllGrades",
            query = "SELECT g FROM Grades g"),
    @NamedQuery(name = "SelectedGrade",
            query = "SELECT g FROM Grades g where g.gradeId = :graad"),
    @NamedQuery(name = "Grade.findByDeterminateTableId",
            query = "SELECT g FROM Grades g where g.DeterminateTableId = :determinateTableId")
})
public class Grade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GradeId")
    private int gradeId;

    @OneToMany(mappedBy = "grade")
    private List<SchoolYear> schoolYears;

    @ManyToOne
    @JoinColumn(name = "DeterminateTableId")
    private DeterminateTable DeterminateTableId;

    //CTOR
    public Grade() {
    }

    public Grade(int grade) {
        this.gradeId = grade;
    }

    //GET SET
    public int getGrade() {
        return gradeId;
    }

    public void setGrade(int grade) {
        this.gradeId = grade;
    }

    public List<SchoolYear> getSchoolYears() {
        return schoolYears;
    }

    public DeterminateTable getDeterminateTableId() {
        return DeterminateTableId;
    }

    public void setDeterminateTableId(DeterminateTable DeterminateTableId) {
        this.DeterminateTableId = DeterminateTableId;
    }

    public void setSchoolYears(List<SchoolYear> schoolYears) {
        this.schoolYears = schoolYears;
    }

    public String getGradeString() {
        return Integer.toString(gradeId);
    }

    @Override
    public String toString() {
        return getGradeString();
    }
}
