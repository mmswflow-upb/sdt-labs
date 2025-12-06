package ro.university.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
@Slf4j
public class FallbackController {

    @GetMapping("/student-service")
    public ResponseEntity<Map<String, Object>> studentServiceFallback() {
        log.warn("Student service is unavailable, returning fallback response");
        return createFallbackResponse("Student Service");
    }

    @GetMapping("/professor-service")
    public ResponseEntity<Map<String, Object>> professorServiceFallback() {
        log.warn("Professor service is unavailable, returning fallback response");
        return createFallbackResponse("Professor Service");
    }

    @GetMapping("/course-service")
    public ResponseEntity<Map<String, Object>> courseServiceFallback() {
        log.warn("Course service is unavailable, returning fallback response");
        return createFallbackResponse("Course Service");
    }

    @GetMapping("/grading-service")
    public ResponseEntity<Map<String, Object>> gradingServiceFallback() {
        log.warn("Grading service is unavailable, returning fallback response");
        return createFallbackResponse("Grading Service");
    }

    private ResponseEntity<Map<String, Object>> createFallbackResponse(String serviceName) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        response.put("error", "Service Unavailable");
        response.put("message", serviceName + " is currently unavailable. Please try again later.");
        response.put("service", serviceName);
        
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
    }
}
