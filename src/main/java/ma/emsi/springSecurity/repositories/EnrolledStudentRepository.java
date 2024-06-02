package ma.emsi.springSecurity.repositories;

import jakarta.transaction.Transactional;
import ma.emsi.springSecurity.entities.EnrolledStudent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional public interface EnrolledStudentRepository extends JpaRepository<EnrolledStudent, Integer> {
    Page<EnrolledStudent> findAll(Pageable pageable);
    Page<EnrolledStudent> findByFirstNameContains(String firstName, Pageable pageable);

}