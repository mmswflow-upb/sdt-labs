package ro.university.professor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.university.professor.client.CourseServiceClient;
import ro.university.professor.dto.CourseAssignmentRequestDTO;
import ro.university.professor.dto.CourseAssignmentResponseDTO;
import ro.university.professor.dto.CourseDTO;
import ro.university.professor.exception.AlreadyAssignedException;
import ro.university.professor.exception.AssignmentNotFoundException;
import ro.university.professor.model.CourseAssignment;
import ro.university.professor.repository.CourseAssignmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseAssignmentService {

    private final CourseAssignmentRepository assignmentRepository;
    private final CourseServiceClient courseServiceClient;

    @Transactional
    public CourseAssignmentResponseDTO assignCourse(CourseAssignmentRequestDTO requestDTO) {
        log.info("Assigning professor {} to course {}", requestDTO.getProfessorId(), requestDTO.getCourseId());

        if (assignmentRepository.existsByProfessorIdAndCourseId(
                requestDTO.getProfessorId(), requestDTO.getCourseId())) {
            throw new AlreadyAssignedException(requestDTO.getProfessorId(), requestDTO.getCourseId());
        }

        CourseDTO course = courseServiceClient.getCourseById(requestDTO.getCourseId());

        CourseAssignment assignment = CourseAssignment.builder()
                .professorId(requestDTO.getProfessorId())
                .courseId(requestDTO.getCourseId())
                .courseCode(course != null ? course.getCode() : null)
                .courseName(course != null ? course.getName() : null)
                .semester(course != null ? course.getSemester() : null)
                .status("ACTIVE")
                .build();

        CourseAssignment savedAssignment = assignmentRepository.save(assignment);
        log.info("Course assigned successfully with assignment id: {}", savedAssignment.getId());
        return mapToResponseDTO(savedAssignment);
    }

    @Transactional(readOnly = true)
    public List<CourseAssignmentResponseDTO> getAssignmentsByProfessorId(Long professorId) {
        log.info("Fetching assignments for professor {}", professorId);
        return assignmentRepository.findByProfessorId(professorId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CourseAssignmentResponseDTO> getAssignmentsByCourseId(Long courseId) {
        log.info("Fetching assignments for course {}", courseId);
        return assignmentRepository.findByCourseId(courseId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CourseAssignmentResponseDTO updateAssignmentStatus(Long assignmentId, String status) {
        log.info("Updating assignment {} to status {}", assignmentId, status);
        CourseAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException(assignmentId));

        assignment.setStatus(status);
        CourseAssignment updatedAssignment = assignmentRepository.save(assignment);
        log.info("Assignment status updated successfully");
        return mapToResponseDTO(updatedAssignment);
    }

    @Transactional
    public void removeAssignment(Long assignmentId) {
        log.info("Removing assignment {}", assignmentId);
        if (!assignmentRepository.existsById(assignmentId)) {
            throw new AssignmentNotFoundException(assignmentId);
        }
        assignmentRepository.deleteById(assignmentId);
        log.info("Assignment removed successfully");
    }

    private CourseAssignmentResponseDTO mapToResponseDTO(CourseAssignment assignment) {
        return CourseAssignmentResponseDTO.builder()
                .id(assignment.getId())
                .professorId(assignment.getProfessorId())
                .courseId(assignment.getCourseId())
                .courseCode(assignment.getCourseCode())
                .courseName(assignment.getCourseName())
                .semester(assignment.getSemester())
                .status(assignment.getStatus())
                .assignedAt(assignment.getAssignedAt())
                .build();
    }
}
