package ro.university.grading.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.university.grading.dto.CourseDTO;

@Component
@Slf4j
public class CourseServiceClientFallback implements CourseServiceClient {
    
    @Override
    public CourseDTO getCourseById(Long courseId) {
        log.warn("Circuit breaker activated for course-service. Returning fallback for course ID: {}", courseId);
        
        // Return a fallback DTO with minimal information
        CourseDTO fallback = new CourseDTO();
        fallback.setId(courseId);
        fallback.setCode("N/A");
        fallback.setName("Course Unavailable");
        
        return fallback;
    }
}
