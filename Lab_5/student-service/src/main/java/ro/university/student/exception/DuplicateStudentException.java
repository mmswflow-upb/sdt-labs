package ro.university.student.exception;

public class DuplicateStudentException extends RuntimeException {
    public DuplicateStudentException(String field, String value) {
        super("Student with " + field + " '" + value + "' already exists");
    }
}
