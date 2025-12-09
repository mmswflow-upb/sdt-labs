package ro.university.course.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.university.course.dto.CourseRequestDTO;
import ro.university.course.dto.CourseResponseDTO;
import ro.university.course.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(
            @Valid @RequestBody CourseRequestDTO requestDTO) {
        CourseResponseDTO response = courseService.createCourse(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id) {
        CourseResponseDTO response = courseService.getCourseById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<CourseResponseDTO> getCourseByCode(@PathVariable String code) {
        CourseResponseDTO response = courseService.getCourseByCode(code);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<CourseResponseDTO>> getCoursesByDepartment(@PathVariable String department) {
        List<CourseResponseDTO> courses = courseService.getCoursesByDepartment(department);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/semester/{semester}")
    public ResponseEntity<List<CourseResponseDTO>> getCoursesBySemester(@PathVariable String semester) {
        List<CourseResponseDTO> courses = courseService.getCoursesBySemester(semester);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/active")
    public ResponseEntity<List<CourseResponseDTO>> getActiveCourses() {
        List<CourseResponseDTO> courses = courseService.getActiveCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) Boolean active) {
        
        List<CourseResponseDTO> courses;
        
        if (department != null && semester != null) {
            // This would require a new method in service, for now just filter by department
            courses = courseService.getCoursesByDepartment(department);
        } else if (department != null) {
            courses = courseService.getCoursesByDepartment(department);
        } else if (semester != null) {
            courses = courseService.getCoursesBySemester(semester);
        } else if (Boolean.TRUE.equals(active)) {
            courses = courseService.getActiveCourses();
        } else {
            courses = courseService.getAllCourses();
        }
        
        return ResponseEntity.ok(courses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequestDTO requestDTO) {
        CourseResponseDTO response = courseService.updateCourse(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<CourseResponseDTO> deactivateCourse(@PathVariable Long id) {
        CourseResponseDTO response = courseService.deactivateCourse(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<CourseResponseDTO> activateCourse(@PathVariable Long id) {
        CourseResponseDTO response = courseService.activateCourse(id);
        return ResponseEntity.ok(response);
    }
}
