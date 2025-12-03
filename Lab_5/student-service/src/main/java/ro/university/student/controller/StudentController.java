package ro.university.student.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.university.student.dto.GradeDTO;
import ro.university.student.dto.StudentRequestDTO;
import ro.university.student.dto.StudentResponseDTO;
import ro.university.student.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(
            @Valid @RequestBody StudentRequestDTO requestDTO) {
        StudentResponseDTO response = studentService.createStudent(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable(value = "id") Long id) {
        StudentResponseDTO response = studentService.getStudentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/student-id/{studentId}")
    public ResponseEntity<StudentResponseDTO> getStudentByStudentId(@PathVariable(value = "studentId") String studentId) {
        StudentResponseDTO response = studentService.getStudentByStudentId(studentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<StudentResponseDTO> getStudentByEmail(@PathVariable(value = "email") String email) {
        StudentResponseDTO response = studentService.getStudentByEmail(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents(
            @RequestParam(value = "major", required = false) String major,
            @RequestParam(value = "active", required = false) Boolean active) {

        List<StudentResponseDTO> students;

        if (major != null) {
            students = studentService.getStudentsByMajor(major);
        } else if (Boolean.TRUE.equals(active)) {
            students = studentService.getActiveStudents();
        } else {
            students = studentService.getAllStudents();
        }

        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody StudentRequestDTO requestDTO) {
        StudentResponseDTO response = studentService.updateStudent(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable(value = "id") Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/grades")
    public ResponseEntity<List<GradeDTO>> getStudentGrades(@PathVariable(value = "id") Long id) {
        List<GradeDTO> grades = studentService.getStudentGrades(id);
        return ResponseEntity.ok(grades);
    }
}
