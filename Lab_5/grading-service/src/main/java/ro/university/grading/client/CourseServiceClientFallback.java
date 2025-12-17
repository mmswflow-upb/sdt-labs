package ro.university.grading.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.university.grading.dto.CourseDTO;

@Component
@Slf4j
public class CourseServiceClientFallback implements CourseServiceClient {

    @Override
    public CourseDTO getCourseById(Long courseId) {
        log.error("CIRCUIT BREAKER ACTIVATED - Course Service is DOWN. Returning fallback for course ID: {}", courseId);

        // Return a fallback DTO with clear indication that the service is unavailable
        CourseDTO fallback = new CourseDTO();
        fallback.setId(courseId);
        fallback.setCode("[ERROR]");
        fallback.setName("[SERVICE DOWN] Course Unavailable");
        fallback.setServiceStatus("SERVICE_DOWN - Course Service is not responding");

        return fallback;
    }
}
