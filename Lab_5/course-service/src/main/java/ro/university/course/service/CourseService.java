package ro.university.course.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.university.course.dto.CourseRequestDTO;
import ro.university.course.dto.CourseResponseDTO;
import ro.university.course.exception.CourseNotFoundException;
import ro.university.course.exception.DuplicateCourseCodeException;
import ro.university.course.model.Course;
import ro.university.course.repository.CourseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional
    public CourseResponseDTO createCourse(CourseRequestDTO requestDTO) {
        log.info("Creating course with code: {}", requestDTO.getCode());
        
        if (courseRepository.existsByCode(requestDTO.getCode())) {
            throw new DuplicateCourseCodeException(requestDTO.getCode());
        }

        Course course = Course.builder()
                .code(requestDTO.getCode())
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .credits(requestDTO.getCredits())
                .department(requestDTO.getDepartment())
                .semester(requestDTO.getSemester())
                .isActive(requestDTO.getIsActive() != null ? requestDTO.getIsActive() : true)
                .build();

        Course savedCourse = courseRepository.save(course);
        log.info("Course created successfully with id: {}", savedCourse.getId());
        return mapToResponseDTO(savedCourse);
    }

    @Transactional(readOnly = true)
    public CourseResponseDTO getCourseById(Long id) {
        log.info("Fetching course with id: {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        return mapToResponseDTO(course);
    }

    @Transactional(readOnly = true)
    public CourseResponseDTO getCourseByCode(String code) {
        log.info("Fetching course with code: {}", code);
        Course course = courseRepository.findByCode(code)
                .orElseThrow(() -> new CourseNotFoundException(code));
        return mapToResponseDTO(course);
    }

    @Transactional(readOnly = true)
    public List<CourseResponseDTO> getAllCourses() {
        log.info("Fetching all courses");
        return courseRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CourseResponseDTO> getCoursesByDepartment(String department) {
        log.info("Fetching courses for department: {}", department);
        return courseRepository.findByDepartment(department).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CourseResponseDTO> getCoursesBySemester(String semester) {
        log.info("Fetching courses for semester: {}", semester);
        return courseRepository.findBySemester(semester).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CourseResponseDTO> getActiveCourses() {
        log.info("Fetching active courses");
        return courseRepository.findByIsActive(true).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO requestDTO) {
        log.info("Updating course with id: {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        // Check if code is being changed and if new code already exists
        if (!course.getCode().equals(requestDTO.getCode()) && 
            courseRepository.existsByCode(requestDTO.getCode())) {
            throw new DuplicateCourseCodeException(requestDTO.getCode());
        }

        course.setCode(requestDTO.getCode());
        course.setName(requestDTO.getName());
        course.setDescription(requestDTO.getDescription());
        course.setCredits(requestDTO.getCredits());
        course.setDepartment(requestDTO.getDepartment());
        course.setSemester(requestDTO.getSemester());
        if (requestDTO.getIsActive() != null) {
            course.setIsActive(requestDTO.getIsActive());
        }

        Course updatedCourse = courseRepository.save(course);
        log.info("Course updated successfully with id: {}", updatedCourse.getId());
        return mapToResponseDTO(updatedCourse);
    }

    @Transactional
    public void deleteCourse(Long id) {
        log.info("Deleting course with id: {}", id);
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
        log.info("Course deleted successfully with id: {}", id);
    }

    @Transactional
    public CourseResponseDTO deactivateCourse(Long id) {
        log.info("Deactivating course with id: {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        course.setIsActive(false);
        Course updatedCourse = courseRepository.save(course);
        log.info("Course deactivated successfully with id: {}", updatedCourse.getId());
        return mapToResponseDTO(updatedCourse);
    }

    @Transactional
    public CourseResponseDTO activateCourse(Long id) {
        log.info("Activating course with id: {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        course.setIsActive(true);
        Course updatedCourse = courseRepository.save(course);
        log.info("Course activated successfully with id: {}", updatedCourse.getId());
        return mapToResponseDTO(updatedCourse);
    }

    private CourseResponseDTO mapToResponseDTO(Course course) {
        return CourseResponseDTO.builder()
                .id(course.getId())
                .code(course.getCode())
                .name(course.getName())
                .description(course.getDescription())
                .credits(course.getCredits())
                .department(course.getDepartment())
                .semester(course.getSemester())
                .isActive(course.getIsActive())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }
}
