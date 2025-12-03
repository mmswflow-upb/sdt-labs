package ro.university.student.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ro.university.student.dto.CourseDTO;

@Component
@RequiredArgsConstructor
@Slf4j
public class CourseServiceClient {

    private final RestClient restClient;

    @Value("${services.course.url:http://course-service:8080}")
    private String courseServiceUrl;

    public CourseDTO getCourseById(Long courseId) {
        try {
            log.info("Fetching course with id {} from course service", courseId);
            return restClient.get()
                    .uri(courseServiceUrl + "/courses/{id}", courseId)
                    .retrieve()
                    .body(CourseDTO.class);
        } catch (Exception e) {
            log.error("Error fetching course from course service: {}", e.getMessage());
            return null;
        }
    }
}
