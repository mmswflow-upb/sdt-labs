package ro.university.grading.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeResponseDTO {

    private Long id;
    private Long studentId;
    private Long professorId;
    private Long courseId;
    private Double value;
    private String comments;
    private String gradeType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
