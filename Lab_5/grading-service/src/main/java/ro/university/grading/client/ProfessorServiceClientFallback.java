package ro.university.grading.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.university.grading.dto.ProfessorDTO;

@Component
@Slf4j
public class ProfessorServiceClientFallback implements ProfessorServiceClient {
    
    @Override
    public ProfessorDTO getProfessorById(Long professorId) {
        log.warn("Circuit breaker activated for professor-service. Returning fallback for professor ID: {}", professorId);
        
        // Return a fallback DTO with minimal information
        ProfessorDTO fallback = new ProfessorDTO();
        fallback.setId(professorId);
        fallback.setFirstName("Professor");
        fallback.setLastName("Unavailable");
        fallback.setEmail("unavailable@university.ro");
        fallback.setProfessorId("N/A");
        
        return fallback;
    }
}
