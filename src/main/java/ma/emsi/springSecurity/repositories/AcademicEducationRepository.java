package ma.emsi.springSecurity.repositories;

import jakarta.transaction.Transactional;
import ma.emsi.springSecurity.entities.AcademicEducation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional

public interface AcademicEducationRepository extends JpaRepository<AcademicEducation, Long> {
    Page<AcademicEducation> findAll(Pageable pageable);
    Page<AcademicEducation> findByProgramName(String programName, Pageable pageable);

}
