package ro.university.grading.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.university.grading.dto.ProfessorDTO;

@Component
@Slf4j
public class ProfessorServiceClientFallback implements ProfessorServiceClient {

    @Override
    public ProfessorDTO getProfessorById(Long professorId) {
        log.error("CIRCUIT BREAKER ACTIVATED - Professor Service is DOWN. Returning fallback for professor ID: {}", professorId);

        // Return a fallback DTO with clear indication that the service is unavailable
        ProfessorDTO fallback = new ProfessorDTO();
        fallback.setId(professorId);
        fallback.setFirstName("[SERVICE DOWN]");
        fallback.setLastName("Professor Unavailable");
        fallback.setEmail("[UNAVAILABLE]");
        fallback.setProfessorId("[ERROR]");
        fallback.setServiceStatus("SERVICE_DOWN - Professor Service is not responding");

        return fallback;
    }
}
