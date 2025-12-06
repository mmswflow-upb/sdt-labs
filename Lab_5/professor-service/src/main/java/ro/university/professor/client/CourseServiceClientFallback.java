package ro.university.professor.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.university.professor.dto.CourseDTO;

@Component
@Slf4j
public class CourseServiceClientFallback implements CourseServiceClient {
    
    @Override
    public CourseDTO getCourseById(Long courseId) {
        log.warn("Circuit breaker activated for course-service. Returning fallback for course ID: {}", courseId);
        
        // Return a fallback DTO with minimal information
        CourseDTO fallback = new CourseDTO();
        fallback.setId(courseId);
        fallback.setName("Course Unavailable");
        fallback.setCode("N/A");
        fallback.setCredits(0);
        fallback.setDepartment("N/A");
        fallback.setSemester("N/A");
        
        return fallback;
    }
}
