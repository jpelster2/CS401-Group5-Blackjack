import java.util.*;
public class Dealer {
	private ArrayList<Card> deck;
	private ArrayList<Card> hand;
	
	public Dealer() {
		//call reset to get a fresh deck
		
	}
	
	public void reset() {
		//fill a new deck
	}
	public void shuffle() {
		//shuffle remaining cards
	}
	public Card dealCard() {
		Card card = new Card(0, "placeholder", CardSuit.CLOVERS);
		return card;
	}
	public void emptyHand() {
		//discard hand
	}
}
