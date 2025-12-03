package ro.university.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.university.course.model.Course;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCode(String code);
    List<Course> findByDepartment(String department);
    List<Course> findBySemester(String semester);
    List<Course> findByIsActive(Boolean isActive);
    List<Course> findByDepartmentAndSemester(String department, String semester);
    boolean existsByCode(String code);
}
