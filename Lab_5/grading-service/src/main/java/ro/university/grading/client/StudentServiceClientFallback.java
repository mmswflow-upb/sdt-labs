package ro.university.grading.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.university.grading.dto.StudentDTO;

@Component
@Slf4j
public class StudentServiceClientFallback implements StudentServiceClient {

    @Override
    public StudentDTO getStudentById(Long studentId) {
        log.error("CIRCUIT BREAKER ACTIVATED - Student Service is DOWN. Returning fallback for student ID: {}", studentId);

        // Return a fallback DTO with clear indication that the service is unavailable
        StudentDTO fallback = new StudentDTO();
        fallback.setId(studentId);
        fallback.setFirstName("[SERVICE DOWN]");
        fallback.setLastName("Student Unavailable");
        fallback.setEmail("[UNAVAILABLE]");
        fallback.setStudentId("[ERROR]");
        fallback.setServiceStatus("SERVICE_DOWN - Student Service is not responding");

        return fallback;
    }
}
