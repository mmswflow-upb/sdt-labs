package ro.university.professor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.university.professor.model.CourseAssignment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseAssignmentRepository extends JpaRepository<CourseAssignment, Long> {
    List<CourseAssignment> findByProfessorId(Long professorId);
    List<CourseAssignment> findByCourseId(Long courseId);
    Optional<CourseAssignment> findByProfessorIdAndCourseId(Long professorId, Long courseId);
    boolean existsByProfessorIdAndCourseId(Long professorId, Long courseId);
    List<CourseAssignment> findByProfessorIdAndStatus(Long professorId, String status);
}
