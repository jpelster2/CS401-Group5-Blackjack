import java.util.ArrayList;

public class Player {
	private String id; 
	private String username; 
	private String password; 
	private float balance;
	private ArrayList<Card> hand; 
	private float currentBet; 
	
	public Player(String username, String password, float balance){
		setUsername(username); 
		setId(username);
		setPassword(password);
		setBalance(balance);
		ArrayList<Card> hand = new ArrayList<Card>(); 
		setHand(hand);
	}
	public Player() {
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setHand(ArrayList<Card>hand) {
		this.hand = hand;
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
	public ArrayList<Card> getHand() {
		return hand; 
	}
	public void addFunds(float funds) {
		float newBalance = getBalance() + funds; 
		setBalance(newBalance); 
	}
	public void removeFunds(float funds) {
		float newBalance = getBalance() - funds;
		if (newBalance < 0) {
			newBalance = 0; 
		}
		setBalance(newBalance);
	}
	public void increaseBet(float amount) {
		float newBet = getCurrentBet() + amount; 
		setCurrentBet(newBet); 
	}
	public void addCardToHand(Card card){
		ArrayList<Card> hand = getHand(); 
		hand.add(card);
	}
	public void emptyHand() {
		ArrayList<Card>hand = new ArrayList<Card>(); 
		setHand(hand); 
	}
	public int currentScore() {
		// Cards 2-10 are worth their face value 
		// Face cards jack, queen, king are worth 10 
		int score = 0;
		boolean hasAce = false; 
		ArrayList<Card>hand = getHand(); 
		for(int i = 0; i < hand.size(); i++) {
			int holding = hand.get(i).getValue(); 
			score+=holding; 
			if(holding == 11) {
				hasAce = true;
			}
		}
		if((score > 21) && (hasAce == true)) {
			score = score - 10; 
			System.out.println("The total score is above 21 therefore the value of Ace has been changed from 11 to 1");
		}
		return score;
	}
}
