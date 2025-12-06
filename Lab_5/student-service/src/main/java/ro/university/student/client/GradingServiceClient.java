package ro.university.student.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ro.university.student.dto.GradeDTO;

import java.util.List;

@FeignClient(
    name = "grading-service",
    fallback = GradingServiceClientFallback.class
)
public interface GradingServiceClient {
    
    @GetMapping("/grades")
    List<GradeDTO> getGradesByStudentId(@RequestParam("studentId") Long studentId);
}
