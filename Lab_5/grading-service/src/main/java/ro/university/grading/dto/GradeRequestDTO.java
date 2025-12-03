package ro.university.grading.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeRequestDTO {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Professor ID is required")
    private Long professorId;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    @NotNull(message = "Grade value is required")
    @Min(value = 0, message = "Grade value must be at least 0")
    @Max(value = 100, message = "Grade value cannot exceed 100")
    private Double value;

    private String comments;

    private String gradeType; // e.g., "MIDTERM", "FINAL", "ASSIGNMENT", "EXAM"
}
