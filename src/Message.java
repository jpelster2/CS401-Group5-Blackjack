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
        System.out.println ("Message Sent: " + this.text);
        System.out.println ("Special Data: " + this.specialData);
        System.out.println ("Action: " + this.action);
    }
    
    public String getText() {
        return this.text;
    }
    
    public String getspecialData() {
        return this.specialData;
    }
    
    public String getAction() {
        return this.action;
    }
}
