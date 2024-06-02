package ma.emsi.springSecurity.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor // lombok
@Entity
public class AcademicEducation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long educationID;
    private String programName;
    private String level;
    private int duration;
    private String curriculum;
    private String institutionName;
    private String location;
    private Date startDate;
    private Date endDate;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name="teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "academicEducation")
    private List<EnrolledStudent> enrolledStudents;

    @Override
    public String toString() {
        return "AcademicEducation{" +
                "educationID=" + educationID +
                ", programName='" + programName + '\'' +
                ", level='" + level + '\'' +
                // other fields, but not 'teacher'
                '}';
    }
}
