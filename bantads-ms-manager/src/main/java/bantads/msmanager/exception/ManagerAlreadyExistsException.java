package bantads.msmanager.exception;

public class ManagerAlreadyExistsException extends RuntimeException {
  public ManagerAlreadyExistsException(String message) {
    super(message);
  }
}