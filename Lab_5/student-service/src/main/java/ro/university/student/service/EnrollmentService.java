package ro.university.student.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.university.student.client.CourseServiceClient;
import ro.university.student.dto.*;
import ro.university.student.exception.*;
import ro.university.student.model.Enrollment;
import ro.university.student.repository.EnrollmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseServiceClient courseServiceClient;

    @Transactional
    public EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO requestDTO) {
        log.info("Enrolling student {} in course {}", requestDTO.getStudentId(), requestDTO.getCourseId());

        // Check if already enrolled
        if (enrollmentRepository.existsByStudentIdAndCourseId(
                requestDTO.getStudentId(), requestDTO.getCourseId())) {
            throw new AlreadyEnrolledException(requestDTO.getStudentId(), requestDTO.getCourseId());
        }

        // Fetch course details from course service
        CourseDTO course = courseServiceClient.getCourseById(requestDTO.getCourseId());

        Enrollment enrollment = Enrollment.builder()
                .studentId(requestDTO.getStudentId())
                .courseId(requestDTO.getCourseId())
                .courseCode(course != null ? course.getCode() : null)
                .courseName(course != null ? course.getName() : null)
                .status("ENROLLED")
                .build();

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        log.info("Student enrolled successfully with enrollment id: {}", savedEnrollment.getId());
        return mapToResponseDTO(savedEnrollment);
    }

    @Transactional(readOnly = true)
    public List<EnrollmentResponseDTO> getEnrollmentsByStudentId(Long studentId) {
        log.info("Fetching enrollments for student {}", studentId);
        return enrollmentRepository.findByStudentId(studentId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EnrollmentResponseDTO> getEnrollmentsByCourseId(Long courseId) {
        log.info("Fetching enrollments for course {}", courseId);
        return enrollmentRepository.findByCourseId(courseId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public EnrollmentResponseDTO updateEnrollmentStatus(Long enrollmentId, String status) {
        log.info("Updating enrollment {} to status {}", enrollmentId, status);
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new EnrollmentNotFoundException(enrollmentId));
        
        enrollment.setStatus(status);
        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);
        log.info("Enrollment status updated successfully");
        return mapToResponseDTO(updatedEnrollment);
    }

    @Transactional
    public void dropEnrollment(Long enrollmentId) {
        log.info("Dropping enrollment {}", enrollmentId);
        if (!enrollmentRepository.existsById(enrollmentId)) {
            throw new EnrollmentNotFoundException(enrollmentId);
        }
        enrollmentRepository.deleteById(enrollmentId);
        log.info("Enrollment dropped successfully");
    }

    private EnrollmentResponseDTO mapToResponseDTO(Enrollment enrollment) {
        return EnrollmentResponseDTO.builder()
                .id(enrollment.getId())
                .studentId(enrollment.getStudentId())
                .courseId(enrollment.getCourseId())
                .courseCode(enrollment.getCourseCode())
                .courseName(enrollment.getCourseName())
                .status(enrollment.getStatus())
                .enrolledAt(enrollment.getEnrolledAt())
                .build();
    }
}
