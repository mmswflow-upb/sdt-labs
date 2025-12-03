package ro.university.professor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.university.professor.model.Professor;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByEmail(String email);
    Optional<Professor> findByProfessorId(String professorId);
    List<Professor> findByDepartment(String department);
    List<Professor> findByIsActive(Boolean isActive);
    boolean existsByEmail(String email);
    boolean existsByProfessorId(String professorId);
}
