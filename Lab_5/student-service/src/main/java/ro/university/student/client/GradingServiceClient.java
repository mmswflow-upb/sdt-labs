package ro.university.student.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ro.university.student.dto.GradeDTO;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GradingServiceClient {

    private final RestClient restClient;

    @Value("${services.grading.url:http://grading-service:8080}")
    private String gradingServiceUrl;

    public List<GradeDTO> getGradesByStudentId(Long studentId) {
        try {
            log.info("Fetching grades for student {} from grading service", studentId);
            return restClient.get()
                    .uri(gradingServiceUrl + "/grades?studentId={studentId}", studentId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<GradeDTO>>() {});
        } catch (Exception e) {
            log.error("Error fetching grades from grading service: {}", e.getMessage());
            return List.of();
        }
    }
}
