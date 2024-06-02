package ma.emsi.springSecurity.repositories;

import jakarta.transaction.Transactional;
import ma.emsi.springSecurity.entities.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Page<Teacher> findAll(Pageable pageable);
}