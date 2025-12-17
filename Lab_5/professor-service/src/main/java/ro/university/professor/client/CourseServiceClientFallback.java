package ro.university.professor.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.university.professor.dto.CourseDTO;

@Component
@Slf4j
public class CourseServiceClientFallback implements CourseServiceClient {

    @Override
    public CourseDTO getCourseById(Long courseId) {
        log.error("CIRCUIT BREAKER ACTIVATED - Course Service is DOWN. Returning fallback for course ID: {}", courseId);

        // Return a fallback DTO with clear indication that the service is unavailable
        CourseDTO fallback = new CourseDTO();
        fallback.setId(courseId);
        fallback.setName("[SERVICE DOWN] Course Unavailable");
        fallback.setCode("[ERROR]");
        fallback.setDescription("Course Service is currently unavailable. Please try again later.");
        fallback.setCredits(-1);
        fallback.setDepartment("[UNAVAILABLE]");
        fallback.setSemester("[UNAVAILABLE]");
        fallback.setServiceStatus("SERVICE_DOWN - Course Service is not responding");

        return fallback;
    }
}
