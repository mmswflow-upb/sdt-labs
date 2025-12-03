package ro.university.professor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseAssignmentResponseDTO {

    private Long id;
    private Long professorId;
    private Long courseId;
    private String courseCode;
    private String courseName;
    private String semester;
    private String status;
    private LocalDateTime assignedAt;
}
