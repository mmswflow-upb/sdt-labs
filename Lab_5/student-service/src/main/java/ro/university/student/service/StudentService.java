package ro.university.student.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.university.student.client.GradingServiceClient;
import ro.university.student.dto.GradeDTO;
import ro.university.student.dto.StudentRequestDTO;
import ro.university.student.dto.StudentResponseDTO;
import ro.university.student.exception.DuplicateStudentException;
import ro.university.student.exception.StudentNotFoundException;
import ro.university.student.model.Student;
import ro.university.student.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;
    private final GradingServiceClient gradingServiceClient;

    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO requestDTO) {
        log.info("Creating student with email: {}", requestDTO.getEmail());

        if (studentRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateStudentException("email", requestDTO.getEmail());
        }

        if (studentRepository.existsByStudentId(requestDTO.getStudentId())) {
            throw new DuplicateStudentException("studentId", requestDTO.getStudentId());
        }

        Student student = Student.builder()
                .firstName(requestDTO.getFirstName())
                .lastName(requestDTO.getLastName())
                .email(requestDTO.getEmail())
                .studentId(requestDTO.getStudentId())
                .major(requestDTO.getMajor())
                .enrollmentDate(requestDTO.getEnrollmentDate())
                .phoneNumber(requestDTO.getPhoneNumber())
                .address(requestDTO.getAddress())
                .isActive(requestDTO.getIsActive() != null ? requestDTO.getIsActive() : true)
                .build();

        Student savedStudent = studentRepository.save(student);
        log.info("Student created successfully with id: {}", savedStudent.getId());
        return mapToResponseDTO(savedStudent);
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentById(Long id) {
        log.info("Fetching student with id: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        return mapToResponseDTO(student);
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentByStudentId(String studentId) {
        log.info("Fetching student with student ID: {}", studentId);
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
        return mapToResponseDTO(student);
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentByEmail(String email) {
        log.info("Fetching student with email: {}", email);
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new StudentNotFoundException(email));
        return mapToResponseDTO(student);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAllStudents() {
        log.info("Fetching all students");
        return studentRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getStudentsByMajor(String major) {
        log.info("Fetching students for major: {}", major);
        return studentRepository.findByMajor(major).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getActiveStudents() {
        log.info("Fetching active students");
        return studentRepository.findByIsActive(true).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO requestDTO) {
        log.info("Updating student with id: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        // Check if email is being changed and if new email already exists
        if (!student.getEmail().equals(requestDTO.getEmail()) &&
                studentRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateStudentException("email", requestDTO.getEmail());
        }

        // Check if studentId is being changed and if new studentId already exists
        if (!student.getStudentId().equals(requestDTO.getStudentId()) &&
                studentRepository.existsByStudentId(requestDTO.getStudentId())) {
            throw new DuplicateStudentException("studentId", requestDTO.getStudentId());
        }

        student.setFirstName(requestDTO.getFirstName());
        student.setLastName(requestDTO.getLastName());
        student.setEmail(requestDTO.getEmail());
        student.setStudentId(requestDTO.getStudentId());
        student.setMajor(requestDTO.getMajor());
        student.setEnrollmentDate(requestDTO.getEnrollmentDate());
        student.setPhoneNumber(requestDTO.getPhoneNumber());
        student.setAddress(requestDTO.getAddress());
        if (requestDTO.getIsActive() != null) {
            student.setIsActive(requestDTO.getIsActive());
        }

        Student updatedStudent = studentRepository.save(student);
        log.info("Student updated successfully with id: {}", updatedStudent.getId());
        return mapToResponseDTO(updatedStudent);
    }

    @Transactional
    public void deleteStudent(Long id) {
        log.info("Deleting student with id: {}", id);
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
        log.info("Student deleted successfully with id: {}", id);
    }

    @Transactional(readOnly = true)
    public List<GradeDTO> getStudentGrades(Long studentId) {
        log.info("Fetching grades for student: {}", studentId);
        // Verify student exists
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(studentId);
        }
        return gradingServiceClient.getGradesByStudentId(studentId);
    }

    private StudentResponseDTO mapToResponseDTO(Student student) {
        return StudentResponseDTO.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .studentId(student.getStudentId())
                .major(student.getMajor())
                .enrollmentDate(student.getEnrollmentDate())
                .phoneNumber(student.getPhoneNumber())
                .address(student.getAddress())
                .isActive(student.getIsActive())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}
