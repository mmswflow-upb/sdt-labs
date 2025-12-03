package ro.university.professor.exception;

public class ProfessorNotFoundException extends RuntimeException {
    public ProfessorNotFoundException(Long id) {
        super("Professor not found with id: " + id);
    }

    public ProfessorNotFoundException(String professorId) {
        super("Professor not found with professor ID: " + professorId);
    }
}
