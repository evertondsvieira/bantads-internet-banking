package bantads.account_command.exceptions;

public class OperationBlockedByBusinessRule extends IllegalArgumentException{
  public OperationBlockedByBusinessRule(){
    super();
  }
  public OperationBlockedByBusinessRule(String s){
    super(s);
  }
  public OperationBlockedByBusinessRule(String message, Throwable cause) {
    super(message, cause);
 }

 public OperationBlockedByBusinessRule(Throwable cause) {
    super(cause);
 }
}
