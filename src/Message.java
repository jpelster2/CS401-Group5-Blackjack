import java.io.Serializable;

public class Message implements Serializable {
    private MessageType type;
    private String action;
    private String text;
    private int specialData;

    public Message(MessageType type, String action, String text, int specialData) {
        this.type = type;
        this.action = action;
        this.text = text;
        this.specialData = specialData;
    }

    public void Messageprint() {
        System.out.println ("Message Sent: " + this.text);
        System.out.println ("Special Data: " + this.specialData);
        System.out.println ("Action: " + this.action);
    }
    
    public MessageType getType() {
        return this.type;
    }
    
    public String getText() {
        return this.text;
    }
    
    public int getspecialData() {
        return this.specialData;
    }
    
    public String getAction() {
        return this.action;
    }
}
