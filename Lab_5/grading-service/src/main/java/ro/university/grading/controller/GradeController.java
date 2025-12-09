package ro.university.grading.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.university.grading.dto.GradeRequestDTO;
import ro.university.grading.dto.GradeResponseDTO;
import ro.university.grading.service.GradeService;

import java.util.List;

@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    public ResponseEntity<GradeResponseDTO> createGrade(
            @Valid @RequestBody GradeRequestDTO requestDTO) {
        GradeResponseDTO response = gradeService.createGrade(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeResponseDTO> getGradeById(@PathVariable Long id) {
        GradeResponseDTO response = gradeService.getGradeById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GradeResponseDTO>> getGradesByStudentId(@PathVariable Long studentId) {
        List<GradeResponseDTO> grades = gradeService.getGradesByStudentId(studentId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<GradeResponseDTO>> getGradesByProfessorId(@PathVariable Long professorId) {
        List<GradeResponseDTO> grades = gradeService.getGradesByProfessorId(professorId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<GradeResponseDTO>> getGradesByCourseId(@PathVariable Long courseId) {
        List<GradeResponseDTO> grades = gradeService.getGradesByCourseId(courseId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping
    public ResponseEntity<List<GradeResponseDTO>> getGrades(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long professorId,
            @RequestParam(required = false) Long courseId) {

        List<GradeResponseDTO> grades;

        if (studentId != null) {
            grades = gradeService.getGradesByStudentId(studentId);
        } else if (professorId != null) {
            grades = gradeService.getGradesByProfessorId(professorId);
        } else if (courseId != null) {
            grades = gradeService.getGradesByCourseId(courseId);
        } else {
            grades = gradeService.getAllGrades();
        }

        return ResponseEntity.ok(grades);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeResponseDTO> updateGrade(
            @PathVariable Long id,
            @Valid @RequestBody GradeRequestDTO requestDTO) {
        GradeResponseDTO response = gradeService.updateGrade(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }
}
