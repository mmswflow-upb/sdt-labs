package ro.university.professor.dto;

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
public class ProfessorRequestDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Valid email is required")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Professor ID is required")
    private String professorId;

    @NotBlank(message = "Department is required")
    private String department;

    private String specialization;

    @NotNull(message = "Hire date is required")
    private LocalDate hireDate;

    private String officeLocation;

    private String phoneNumber;

    @Builder.Default
    private Boolean isActive = true;
}
