package ro.university.grading.exception;

public class ExternalServiceException extends RuntimeException {
    public ExternalServiceException(String service, String message) {
        super("Error communicating with " + service + ": " + message);
    }
}
