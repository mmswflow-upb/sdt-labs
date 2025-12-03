package ro.university.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Valid email is required")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Student ID is required")
    private String studentId;

    @NotBlank(message = "Major is required")
    private String major;

    @NotNull(message = "Enrollment date is required")
    private LocalDate enrollmentDate;

    private String phoneNumber;

    private String address;

    @Builder.Default
    private Boolean isActive = true;
}
