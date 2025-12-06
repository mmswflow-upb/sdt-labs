package ro.university.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Health check endpoints
                .route("student-health", r -> r
                        .path("/health/students")
                        .filters(f -> f.rewritePath("/health/students", "/actuator/health"))
                        .uri("${STUDENT_SERVICE_URL:http://localhost:8081}"))
                .route("professor-health", r -> r
                        .path("/health/professors")
                        .filters(f -> f.rewritePath("/health/professors", "/actuator/health"))
                        .uri("${PROFESSOR_SERVICE_URL:http://localhost:8082}"))
                .route("course-health", r -> r
                        .path("/health/courses")
                        .filters(f -> f.rewritePath("/health/courses", "/actuator/health"))
                        .uri("${COURSE_SERVICE_URL:http://localhost:8083}"))
                .route("grading-health", r -> r
                        .path("/health/grading")
                        .filters(f -> f.rewritePath("/health/grading", "/actuator/health"))
                        .uri("${GRADING_SERVICE_URL:http://localhost:8084}"))
                .build();
    }
}
