package ro.university.professor.exception;

public class AlreadyAssignedException extends RuntimeException {
    public AlreadyAssignedException(Long professorId, Long courseId) {
        super("Professor " + professorId + " is already assigned to course " + courseId);
    }
}
