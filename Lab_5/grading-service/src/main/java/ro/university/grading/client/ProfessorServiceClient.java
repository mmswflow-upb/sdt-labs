package ro.university.grading.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ro.university.grading.dto.ProfessorDTO;

@FeignClient(
    name = "professor-service",
    fallback = ProfessorServiceClientFallback.class
)
public interface ProfessorServiceClient {
    
    @GetMapping("/professors/{id}")
    ProfessorDTO getProfessorById(@PathVariable("id") Long professorId);
}
