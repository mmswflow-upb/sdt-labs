package ro.university.grading.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.university.grading.dto.StudentDTO;

@Component
@Slf4j
public class StudentServiceClientFallback implements StudentServiceClient {
    
    @Override
    public StudentDTO getStudentById(Long studentId) {
        log.warn("Circuit breaker activated for student-service. Returning fallback for student ID: {}", studentId);
        
        // Return a fallback DTO with minimal information
        StudentDTO fallback = new StudentDTO();
        fallback.setId(studentId);
        fallback.setFirstName("Student");
        fallback.setLastName("Unavailable");
        fallback.setEmail("unavailable@university.ro");
        fallback.setStudentId("N/A");
        
        return fallback;
    }
}
