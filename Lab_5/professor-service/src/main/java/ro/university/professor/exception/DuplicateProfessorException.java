package ro.university.professor.exception;

public class DuplicateProfessorException extends RuntimeException {
    public DuplicateProfessorException(String field, String value) {
        super("Professor with " + field + " '" + value + "' already exists");
    }
}
