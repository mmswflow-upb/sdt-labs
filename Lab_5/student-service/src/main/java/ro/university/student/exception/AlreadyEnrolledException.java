package ro.university.student.exception;

public class AlreadyEnrolledException extends RuntimeException {
    public AlreadyEnrolledException(Long studentId, Long courseId) {
        super("Student " + studentId + " is already enrolled in course " + courseId);
    }
}
