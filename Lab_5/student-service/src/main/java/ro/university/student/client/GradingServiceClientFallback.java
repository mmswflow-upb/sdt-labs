package ro.university.student.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.university.student.dto.GradeDTO;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class GradingServiceClientFallback implements GradingServiceClient {
    
    @Override
    public List<GradeDTO> getGradesByStudentId(Long studentId) {
        log.warn("Circuit breaker activated for grading-service. Returning empty grades for student ID: {}", studentId);
        
        // Return empty list when service is unavailable
        return new ArrayList<>();
    }
}
