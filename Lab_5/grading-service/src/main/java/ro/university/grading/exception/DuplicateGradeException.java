package ro.university.grading.exception;

public class DuplicateGradeException extends RuntimeException {
    public DuplicateGradeException(Long studentId, Long courseId) {
        super("Grade already exists for student " + studentId + " in course " + courseId);
    }
}
