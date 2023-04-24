public class Message {
    private MessageType type;
    private String action;
    private String text;
    private int specialData;

    public Message(String action, String text, int specialData) {
        this.type = MessageType.LOGIN;
        this.action = action;
        this.text = text;
        this.specialData = specialData;
    }

    public void Messageprint() {
        System.out.println ("Message sent: " + this.text);
    }

    public String getMessage() {
        return this.text + this.specialData + " (" + this.action + ")";
    }
}
