package ma.emsi.springSecurity.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor // lombok

@Entity @Table(name = "Teachers")
/*public class Teacher {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String diplome;

    private Integer echelle;

    private double salaire;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;


}*/

public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherID;
    private String firstName;
    private String lastName;
    private String subject;
    private String qualification;
    private int experience;
    private String email;
    private String phoneNumber;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<AcademicEducation> academicEducations;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToMany
    @JoinTable(
            name = "teacher_student",
            joinColumns = @JoinColumn(name = "teacherID"),
            inverseJoinColumns = @JoinColumn(name = "studentID")
    )
    private List<EnrolledStudent> enrolledStudents;

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherID=" + teacherID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                // other fields, but not 'courses'
                '}';
    }
}