package ro.university.grading.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ro.university.grading.dto.StudentDTO;
import ro.university.grading.exception.ExternalServiceException;

@Component
@RequiredArgsConstructor
@Slf4j
public class StudentServiceClient {

    private final RestClient restClient;

    @Value("${services.student.url:http://student-service:8080}")
    private String studentServiceUrl;

    public StudentDTO getStudentById(Long studentId) {
        try {
            log.info("Fetching student with id {} from student service", studentId);
            StudentDTO student = restClient.get()
                    .uri(studentServiceUrl + "/students/{id}", studentId)
                    .retrieve()
                    .body(StudentDTO.class);
            
            if (student == null) {
                throw new ExternalServiceException("Student Service", "Student not found with id: " + studentId);
            }
            return student;
        } catch (Exception e) {
            log.error("Error fetching student from student service: {}", e.getMessage());
            throw new ExternalServiceException("Student Service", e.getMessage());
        }
    }
}
