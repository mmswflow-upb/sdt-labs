package ro.university.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.university.student.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    Optional<Student> findByStudentId(String studentId);
    List<Student> findByMajor(String major);
    List<Student> findByIsActive(Boolean isActive);
    boolean existsByEmail(String email);
    boolean existsByStudentId(String studentId);
}
