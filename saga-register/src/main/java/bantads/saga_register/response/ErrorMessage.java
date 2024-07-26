package bantads.saga_register.response;

public class ErrorMessage {
    private final String type="erro";
    private String message;

    public ErrorMessage() {}

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}