package bantads.account_command.exceptions;

public class RecordNotFoundException extends IllegalArgumentException{
  public RecordNotFoundException(){
    super();
  }
  public RecordNotFoundException(String s){
    super(s);
  }
  public RecordNotFoundException(String message, Throwable cause) {
    super(message, cause);
 }

 public RecordNotFoundException(Throwable cause) {
    super(cause);
 }
}
