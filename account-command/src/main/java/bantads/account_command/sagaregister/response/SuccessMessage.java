package bantads.account_command.sagaregister.response;

public class SuccessMessage {
    private final String type="sucesso";
    private String data;

    public SuccessMessage() {}

    public SuccessMessage(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}