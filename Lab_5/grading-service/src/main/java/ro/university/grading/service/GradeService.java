package ro.university.grading.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.university.grading.client.CourseServiceClient;
import ro.university.grading.client.ProfessorServiceClient;
import ro.university.grading.client.StudentServiceClient;
import ro.university.grading.dto.GradeRequestDTO;
import ro.university.grading.dto.GradeResponseDTO;
import ro.university.grading.exception.DuplicateGradeException;
import ro.university.grading.exception.GradeNotFoundException;
import ro.university.grading.model.Grade;
import ro.university.grading.repository.GradeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentServiceClient studentServiceClient;
    private final ProfessorServiceClient professorServiceClient;
    private final CourseServiceClient courseServiceClient;

    @Transactional
    public GradeResponseDTO createGrade(GradeRequestDTO requestDTO) {
        log.info("Creating grade for student {} in course {}", requestDTO.getStudentId(), requestDTO.getCourseId());

        // Validate student, professor, and course exist by calling their respective services
        studentServiceClient.getStudentById(requestDTO.getStudentId());
        professorServiceClient.getProfessorById(requestDTO.getProfessorId());
        courseServiceClient.getCourseById(requestDTO.getCourseId());

        // Check if grade already exists for this student-course combination
        if (gradeRepository.existsByStudentIdAndCourseId(requestDTO.getStudentId(), requestDTO.getCourseId())) {
            throw new DuplicateGradeException(requestDTO.getStudentId(), requestDTO.getCourseId());
        }

        Grade grade = Grade.builder()
                .studentId(requestDTO.getStudentId())
                .professorId(requestDTO.getProfessorId())
                .courseId(requestDTO.getCourseId())
                .value(requestDTO.getValue())
                .comments(requestDTO.getComments())
                .gradeType(requestDTO.getGradeType())
                .build();

        Grade savedGrade = gradeRepository.save(grade);
        log.info("Grade created successfully with id: {}", savedGrade.getId());
        return mapToResponseDTO(savedGrade);
    }

    @Transactional(readOnly = true)
    public GradeResponseDTO getGradeById(Long id) {
        log.info("Fetching grade with id: {}", id);
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new GradeNotFoundException(id));
        return mapToResponseDTO(grade);
    }

    @Transactional(readOnly = true)
    public List<GradeResponseDTO> getAllGrades() {
        log.info("Fetching all grades");
        return gradeRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GradeResponseDTO> getGradesByStudentId(Long studentId) {
        log.info("Fetching grades for student {}", studentId);
        // Validate student exists
        studentServiceClient.getStudentById(studentId);
        return gradeRepository.findByStudentId(studentId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GradeResponseDTO> getGradesByProfessorId(Long professorId) {
        log.info("Fetching grades for professor {}", professorId);
        // Validate professor exists
        professorServiceClient.getProfessorById(professorId);
        return gradeRepository.findByProfessorId(professorId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GradeResponseDTO> getGradesByCourseId(Long courseId) {
        log.info("Fetching grades for course {}", courseId);
        // Validate course exists
        courseServiceClient.getCourseById(courseId);
        return gradeRepository.findByCourseId(courseId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public GradeResponseDTO updateGrade(Long id, GradeRequestDTO requestDTO) {
        log.info("Updating grade with id: {}", id);
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new GradeNotFoundException(id));

        // Validate that entities still exist
        studentServiceClient.getStudentById(requestDTO.getStudentId());
        professorServiceClient.getProfessorById(requestDTO.getProfessorId());
        courseServiceClient.getCourseById(requestDTO.getCourseId());

        // Check if student-course combination is changing and if new combination already exists
        if ((!grade.getStudentId().equals(requestDTO.getStudentId()) || 
             !grade.getCourseId().equals(requestDTO.getCourseId())) &&
            gradeRepository.existsByStudentIdAndCourseId(requestDTO.getStudentId(), requestDTO.getCourseId())) {
            throw new DuplicateGradeException(requestDTO.getStudentId(), requestDTO.getCourseId());
        }

        grade.setStudentId(requestDTO.getStudentId());
        grade.setProfessorId(requestDTO.getProfessorId());
        grade.setCourseId(requestDTO.getCourseId());
        grade.setValue(requestDTO.getValue());
        grade.setComments(requestDTO.getComments());
        grade.setGradeType(requestDTO.getGradeType());

        Grade updatedGrade = gradeRepository.save(grade);
        log.info("Grade updated successfully with id: {}", updatedGrade.getId());
        return mapToResponseDTO(updatedGrade);
    }

    @Transactional
    public void deleteGrade(Long id) {
        log.info("Deleting grade with id: {}", id);
        if (!gradeRepository.existsById(id)) {
            throw new GradeNotFoundException(id);
        }
        gradeRepository.deleteById(id);
        log.info("Grade deleted successfully with id: {}", id);
    }

    private GradeResponseDTO mapToResponseDTO(Grade grade) {
        return GradeResponseDTO.builder()
                .id(grade.getId())
                .studentId(grade.getStudentId())
                .professorId(grade.getProfessorId())
                .courseId(grade.getCourseId())
                .value(grade.getValue())
                .comments(grade.getComments())
                .gradeType(grade.getGradeType())
                .createdAt(grade.getCreatedAt())
                .updatedAt(grade.getUpdatedAt())
                .build();
    }
}
