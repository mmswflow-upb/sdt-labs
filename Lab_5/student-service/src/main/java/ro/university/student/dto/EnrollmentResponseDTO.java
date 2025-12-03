package ro.university.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponseDTO {

    private Long id;
    private Long studentId;
    private Long courseId;
    private String courseCode;
    private String courseName;
    private String status;
    private LocalDateTime enrolledAt;
}
