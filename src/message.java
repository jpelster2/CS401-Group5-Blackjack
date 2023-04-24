public class message {
    private String text;
    private int specialData;

    public message(String text, int specialData) {
        this.text = text;
        this.specialData = specialData;
    }

    public void messageprint() {
        System.out.println ("Message sent: " + this.text);
    }

    public String getMessage() {
        return this.text + this.specialData;
    }
}
