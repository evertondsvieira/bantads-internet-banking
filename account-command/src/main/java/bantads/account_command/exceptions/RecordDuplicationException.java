package bantads.account_command.exceptions;

public class RecordDuplicationException extends IllegalArgumentException{
  public RecordDuplicationException(){
    super();
  }
  public RecordDuplicationException(String s){
    super(s);
  }
  public RecordDuplicationException(String message, Throwable cause) {
    super(message, cause);
 }

 public RecordDuplicationException(Throwable cause) {
    super(cause);
 }
}
