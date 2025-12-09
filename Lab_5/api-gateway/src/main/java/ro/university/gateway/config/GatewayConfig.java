package ro.university.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Value("${STUDENT_SERVICE_URL:http://localhost:8081}")
    private String studentServiceUrl;

    @Value("${PROFESSOR_SERVICE_URL:http://localhost:8082}")
    private String professorServiceUrl;

    @Value("${COURSE_SERVICE_URL:http://localhost:8083}")
    private String courseServiceUrl;

    @Value("${GRADING_SERVICE_URL:http://localhost:8084}")
    private String gradingServiceUrl;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Health check endpoints
                .route("student-health", r -> r
                        .path("/health/students")
                        .filters(f -> f.rewritePath("/health/students", "/actuator/health"))
                        .uri(studentServiceUrl))
                .route("professor-health", r -> r
                        .path("/health/professors")
                        .filters(f -> f.rewritePath("/health/professors", "/actuator/health"))
                        .uri(professorServiceUrl))
                .route("course-health", r -> r
                        .path("/health/courses")
                        .filters(f -> f.rewritePath("/health/courses", "/actuator/health"))
                        .uri(courseServiceUrl))
                .route("grading-health", r -> r
                        .path("/health/grading")
                        .filters(f -> f.rewritePath("/health/grading", "/actuator/health"))
                        .uri(gradingServiceUrl))
                .build();
    }
}
