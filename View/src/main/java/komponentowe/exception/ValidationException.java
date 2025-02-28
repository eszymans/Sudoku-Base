package komponentowe.exception;


public class ValidationException extends AppException {
    public ValidationException() {
        super("validation.error");
    }

    public ValidationException(Throwable cause) {
        super("validation.error", cause);
    }

    public ValidationException(String message) {
        super(message);
    }
}