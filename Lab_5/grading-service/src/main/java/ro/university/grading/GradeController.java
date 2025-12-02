package ro.university.grading;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeRepository repository;

    @PostMapping
    public ResponseEntity<Grade> create(@Valid @RequestBody Grade grade) {
        return ResponseEntity.ok(repository.save(grade));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grade> update(@PathVariable Long id,
                                        @Valid @RequestBody Grade updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setStudentId(updated.getStudentId());
                    existing.setProfessorId(updated.getProfessorId());
                    existing.setCourseId(updated.getCourseId());
                    existing.setValue(updated.getValue());
                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Grade>> query(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long professorId,
            @RequestParam(required = false) Long courseId
    ) {
        if (studentId != null) {
            return ResponseEntity.ok(repository.findByStudentId(studentId));
        } else if (professorId != null) {
            return ResponseEntity.ok(repository.findByProfessorId(professorId));
        } else if (courseId != null) {
            return ResponseEntity.ok(repository.findByCourseId(courseId));
        } else {
            return ResponseEntity.ok(repository.findAll());
        }
    }
}
