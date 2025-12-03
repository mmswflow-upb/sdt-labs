package ro.university.professor.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseAssignmentRequestDTO {

    @NotNull(message = "Professor ID is required")
    private Long professorId;

    @NotNull(message = "Course ID is required")
    private Long courseId;
}
