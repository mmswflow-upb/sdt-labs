package ro.university.professor.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ro.university.professor.dto.CourseDTO;

@FeignClient(
    name = "course-service",
    fallback = CourseServiceClientFallback.class
)
public interface CourseServiceClient {
    
    @GetMapping("/courses/{id}")
    CourseDTO getCourseById(@PathVariable("id") Long courseId);
}
