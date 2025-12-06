package ro.university.grading.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ro.university.grading.dto.StudentDTO;

@FeignClient(
    name = "student-service",
    fallback = StudentServiceClientFallback.class
)
public interface StudentServiceClient {
    
    @GetMapping("/students/{id}")
    StudentDTO getStudentById(@PathVariable("id") Long studentId);
}
