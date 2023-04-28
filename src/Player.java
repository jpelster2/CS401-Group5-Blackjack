
public class Player {
	private String id; 
	private String username; 
	private String password; 
	private float balance;
	//private ArrayList<Card> hand; 
	private float currentBet; 
	
	public Player(String username, String password, float balance){
		setUsername(username); 
		setId(username);
		setPassword(password);
		setBalance(balance);
	}
	public Player() {
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword (String password) {
		this.password = password;
	}
	public void setBalance(float balance) {
		this.balance = balance; 
	}
	public void setCurrentBet (float currentBet) {
		this.currentBet = currentBet;
	}
	public String getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password; 
	}
	public float getBalance() {
		return balance; 
	}
	public float getCurrentBet() {
		return currentBet;
	}
	public void addFunds(float funds) {
		float newBalance = getBalance() + funds; 
		setBalance(newBalance); 
	}
	public void increaseBet(float amount) {
		float newBet = getCurrentBet() + amount; 
		setCurrentBet(newBet); 
	}
	/*
	public void requestCard() {
		
	}
	public void emptyHand() {
		
	}
	public int currentScore() {
		
	}
	*/
}
