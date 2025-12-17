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
        log.error("CIRCUIT BREAKER ACTIVATED - Grading Service is DOWN. Cannot fetch grades for student ID: {}. Returning empty grade list.", studentId);

        // Return empty list when service is unavailable
        // Note: Empty list indicates service is down, not that student has no grades
        return new ArrayList<>();
    }
}
