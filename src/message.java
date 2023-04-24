public class message {
    private String text;
    private int specialData;
    private String status;

    public message(String text, int specialData) {
        this.text = text;
        this.specialData = specialData;
        this.status = status;
    }

    public void messageprint() {
        System.out.println ("Message sent: " + this.text);
    }

    public String getMessage() {
        return this.text + this.specialData + " (" + this.status + ")";
    }
}
