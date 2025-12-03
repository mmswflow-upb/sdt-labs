package ro.university.student.exception;

public class EnrollmentNotFoundException extends RuntimeException {
    public EnrollmentNotFoundException(Long id) {
        super("Enrollment not found with id: " + id);
    }

    public EnrollmentNotFoundException(Long studentId, Long courseId) {
        super("Enrollment not found for student " + studentId + " in course " + courseId);
    }
}
