package ma.emsi.springSecurity.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnrolledStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentID;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Date enrollmentDate;
    private String gradeLevel;
    private String email;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "educationID")
    private AcademicEducation academicEducation;

    @ManyToMany(mappedBy = "enrolledStudents")
    private List<Teacher> teachers;

}

