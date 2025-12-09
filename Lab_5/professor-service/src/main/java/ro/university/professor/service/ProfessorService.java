package ro.university.professor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.university.professor.dto.ProfessorRequestDTO;
import ro.university.professor.dto.ProfessorResponseDTO;
import ro.university.professor.exception.DuplicateProfessorException;
import ro.university.professor.exception.ProfessorNotFoundException;
import ro.university.professor.model.Professor;
import ro.university.professor.repository.ProfessorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    @Transactional
    public ProfessorResponseDTO createProfessor(ProfessorRequestDTO requestDTO) {
        log.info("Creating professor with email: {}", requestDTO.getEmail());

        if (professorRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateProfessorException("email", requestDTO.getEmail());
        }

        if (professorRepository.existsByProfessorId(requestDTO.getProfessorId())) {
            throw new DuplicateProfessorException("professorId", requestDTO.getProfessorId());
        }

        Professor professor = Professor.builder()
                .firstName(requestDTO.getFirstName())
                .lastName(requestDTO.getLastName())
                .email(requestDTO.getEmail())
                .professorId(requestDTO.getProfessorId())
                .department(requestDTO.getDepartment())
                .specialization(requestDTO.getSpecialization())
                .hireDate(requestDTO.getHireDate())
                .officeLocation(requestDTO.getOfficeLocation())
                .phoneNumber(requestDTO.getPhoneNumber())
                .isActive(requestDTO.getIsActive() != null ? requestDTO.getIsActive() : true)
                .build();

        Professor savedProfessor = professorRepository.save(professor);
        log.info("Professor created successfully with id: {}", savedProfessor.getId());
        return mapToResponseDTO(savedProfessor);
    }

    @Transactional(readOnly = true)
    public ProfessorResponseDTO getProfessorById(Long id) {
        log.info("Fetching professor with id: {}", id);
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ProfessorNotFoundException(id));
        return mapToResponseDTO(professor);
    }

    @Transactional(readOnly = true)
    public ProfessorResponseDTO getProfessorByProfessorId(String professorId) {
        log.info("Fetching professor with professor ID: {}", professorId);
        Professor professor = professorRepository.findByProfessorId(professorId)
                .orElseThrow(() -> new ProfessorNotFoundException(professorId));
        return mapToResponseDTO(professor);
    }

    @Transactional(readOnly = true)
    public ProfessorResponseDTO getProfessorByEmail(String email) {
        log.info("Fetching professor with email: {}", email);
        Professor professor = professorRepository.findByEmail(email)
                .orElseThrow(() -> new ProfessorNotFoundException("Professor not found with email: " + email));
        return mapToResponseDTO(professor);
    }

    @Transactional(readOnly = true)
    public List<ProfessorResponseDTO> getAllProfessors() {
        log.info("Fetching all professors");
        return professorRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProfessorResponseDTO> getProfessorsByDepartment(String department) {
        log.info("Fetching professors for department: {}", department);
        return professorRepository.findByDepartment(department).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProfessorResponseDTO> getActiveProfessors() {
        log.info("Fetching active professors");
        return professorRepository.findByIsActive(true).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProfessorResponseDTO updateProfessor(Long id, ProfessorRequestDTO requestDTO) {
        log.info("Updating professor with id: {}", id);
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ProfessorNotFoundException(id));

        if (!professor.getEmail().equals(requestDTO.getEmail()) &&
                professorRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateProfessorException("email", requestDTO.getEmail());
        }

        if (!professor.getProfessorId().equals(requestDTO.getProfessorId()) &&
                professorRepository.existsByProfessorId(requestDTO.getProfessorId())) {
            throw new DuplicateProfessorException("professorId", requestDTO.getProfessorId());
        }

        professor.setFirstName(requestDTO.getFirstName());
        professor.setLastName(requestDTO.getLastName());
        professor.setEmail(requestDTO.getEmail());
        professor.setProfessorId(requestDTO.getProfessorId());
        professor.setDepartment(requestDTO.getDepartment());
        professor.setSpecialization(requestDTO.getSpecialization());
        professor.setHireDate(requestDTO.getHireDate());
        professor.setOfficeLocation(requestDTO.getOfficeLocation());
        professor.setPhoneNumber(requestDTO.getPhoneNumber());
        if (requestDTO.getIsActive() != null) {
            professor.setIsActive(requestDTO.getIsActive());
        }

        Professor updatedProfessor = professorRepository.save(professor);
        log.info("Professor updated successfully with id: {}", updatedProfessor.getId());
        return mapToResponseDTO(updatedProfessor);
    }

    @Transactional
    public void deleteProfessor(Long id) {
        log.info("Deleting professor with id: {}", id);
        if (!professorRepository.existsById(id)) {
            throw new ProfessorNotFoundException(id);
        }
        professorRepository.deleteById(id);
        log.info("Professor deleted successfully with id: {}", id);
    }

    private ProfessorResponseDTO mapToResponseDTO(Professor professor) {
        return ProfessorResponseDTO.builder()
                .id(professor.getId())
                .firstName(professor.getFirstName())
                .lastName(professor.getLastName())
                .email(professor.getEmail())
                .professorId(professor.getProfessorId())
                .department(professor.getDepartment())
                .specialization(professor.getSpecialization())
                .hireDate(professor.getHireDate())
                .officeLocation(professor.getOfficeLocation())
                .phoneNumber(professor.getPhoneNumber())
                .isActive(professor.getIsActive())
                .createdAt(professor.getCreatedAt())
                .updatedAt(professor.getUpdatedAt())
                .build();
    }
}
