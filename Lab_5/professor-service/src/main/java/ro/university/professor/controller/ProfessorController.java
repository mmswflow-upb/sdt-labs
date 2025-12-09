package ro.university.professor.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.university.professor.dto.ProfessorRequestDTO;
import ro.university.professor.dto.ProfessorResponseDTO;
import ro.university.professor.service.ProfessorService;

import java.util.List;

@RestController
@RequestMapping("/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> createProfessor(
            @Valid @RequestBody ProfessorRequestDTO requestDTO) {
        ProfessorResponseDTO response = professorService.createProfessor(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> getProfessorById(@PathVariable Long id) {
        ProfessorResponseDTO response = professorService.getProfessorById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/professor-id/{professorId}")
    public ResponseEntity<ProfessorResponseDTO> getProfessorByProfessorId(@PathVariable String professorId) {
        ProfessorResponseDTO response = professorService.getProfessorByProfessorId(professorId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email:.+}")
    public ResponseEntity<ProfessorResponseDTO> getProfessorByEmail(@PathVariable String email) {
        ProfessorResponseDTO response = professorService.getProfessorByEmail(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<ProfessorResponseDTO>> getProfessorsByDepartment(@PathVariable String department) {
        List<ProfessorResponseDTO> professors = professorService.getProfessorsByDepartment(department);
        return ResponseEntity.ok(professors);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ProfessorResponseDTO>> getActiveProfessors() {
        List<ProfessorResponseDTO> professors = professorService.getActiveProfessors();
        return ResponseEntity.ok(professors);
    }

    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> getAllProfessors(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Boolean active) {

        List<ProfessorResponseDTO> professors;

        if (department != null) {
            professors = professorService.getProfessorsByDepartment(department);
        } else if (Boolean.TRUE.equals(active)) {
            professors = professorService.getActiveProfessors();
        } else {
            professors = professorService.getAllProfessors();
        }

        return ResponseEntity.ok(professors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> updateProfessor(
            @PathVariable Long id,
            @Valid @RequestBody ProfessorRequestDTO requestDTO) {
        ProfessorResponseDTO response = professorService.updateProfessor(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessor(id);
        return ResponseEntity.noContent().build();
    }
}
