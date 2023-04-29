
public class Card {
	private int value;
	private String name;
	private CardSuit suit;
	
	public Card() {

	}
	public Card(int value, String name, CardSuit suit) {
		this.value = value;
		this.name = name;
		this.suit = suit;
	}
	
	public int getValue() {
		return this.value;
	}
	public CardSuit getSuit() {
		return this.suit;
	}
	public String getName() {
		return this.name;
	}
	public void setAceValue(int choice) {
		this.value = choice;
	}
	
}
