package bantads.account_command.exceptions;

public class RecordCannotBeDeleted extends IllegalArgumentException{
  public RecordCannotBeDeleted(){
    super();
  }
  public RecordCannotBeDeleted(String s){
    super(s);
  }
  public RecordCannotBeDeleted(String message, Throwable cause) {
    super(message, cause);
 }

 public RecordCannotBeDeleted(Throwable cause) {
    super(cause);
 }
}
