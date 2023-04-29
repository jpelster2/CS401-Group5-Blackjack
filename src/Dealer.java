import java.util.*;
public class Dealer {
	private ArrayList<Card> deck;
	private ArrayList<Card> hand;
	
	public Dealer() {
		//call reset to get a fresh deck
		
	}
	
	public void reset() {
		//fill a new deck, loop by suit and # of decks
		
	}
	public void shuffle() {
		//shuffle remaining cards
	}
	public Card dealCard() {
		Card card = new Card(0, "placeholder", CardSuit.CLUBS);//should eventually draw from deck
		return card;
	}
	public int getRemaining() {//deck size
		int total = deck.size();
		return total;
	}
	public int handTotal() {
		int total = 0;
		for (Card i: hand) {
			total += i.getValue();
		}
		return total;
	}
	public void emptyHand() {
		//discard hand
		hand.clear();
	}
	
}
