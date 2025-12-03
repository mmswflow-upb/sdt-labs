package ro.university.professor.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.university.professor.dto.CourseAssignmentRequestDTO;
import ro.university.professor.dto.CourseAssignmentResponseDTO;
import ro.university.professor.service.CourseAssignmentService;

import java.util.List;

@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
public class CourseAssignmentController {

    private final CourseAssignmentService assignmentService;

    @PostMapping
    public ResponseEntity<CourseAssignmentResponseDTO> assignCourse(
            @Valid @RequestBody CourseAssignmentRequestDTO requestDTO) {
        CourseAssignmentResponseDTO response = assignmentService.assignCourse(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<CourseAssignmentResponseDTO>> getAssignmentsByProfessorId(
            @PathVariable Long professorId) {
        List<CourseAssignmentResponseDTO> assignments = assignmentService.getAssignmentsByProfessorId(professorId);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<CourseAssignmentResponseDTO>> getAssignmentsByCourseId(
            @PathVariable Long courseId) {
        List<CourseAssignmentResponseDTO> assignments = assignmentService.getAssignmentsByCourseId(courseId);
        return ResponseEntity.ok(assignments);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CourseAssignmentResponseDTO> updateAssignmentStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        CourseAssignmentResponseDTO response = assignmentService.updateAssignmentStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAssignment(@PathVariable Long id) {
        assignmentService.removeAssignment(id);
        return ResponseEntity.noContent().build();
    }
}
