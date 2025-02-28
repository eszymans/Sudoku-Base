package komponentowe.exception;

public class DataAccessException extends AppException {
  public DataAccessException() {
    super("data.access.error");
  }

  public DataAccessException(Throwable cause) {
    super("data.access.error", cause);
  }

  public DataAccessException(String message) {
    super(message);
  }
}
