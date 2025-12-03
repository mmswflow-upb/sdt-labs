package ro.university.grading.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ro.university.grading.dto.ProfessorDTO;
import ro.university.grading.exception.ExternalServiceException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProfessorServiceClient {

    private final RestClient restClient;

    @Value("${services.professor.url:http://professor-service:8080}")
    private String professorServiceUrl;

    public ProfessorDTO getProfessorById(Long professorId) {
        try {
            log.info("Fetching professor with id {} from professor service", professorId);
            ProfessorDTO professor = restClient.get()
                    .uri(professorServiceUrl + "/professors/{id}", professorId)
                    .retrieve()
                    .body(ProfessorDTO.class);
            
            if (professor == null) {
                throw new ExternalServiceException("Professor Service", "Professor not found with id: " + professorId);
            }
            return professor;
        } catch (Exception e) {
            log.error("Error fetching professor from professor service: {}", e.getMessage());
            throw new ExternalServiceException("Professor Service", e.getMessage());
        }
    }
}
