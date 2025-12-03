package ro.university.professor.exception;

public class AssignmentNotFoundException extends RuntimeException {
    public AssignmentNotFoundException(Long id) {
        super("Course assignment not found with id: " + id);
    }

    public AssignmentNotFoundException(Long professorId, Long courseId) {
        super("Course assignment not found for professor " + professorId + " and course " + courseId);
    }
}
