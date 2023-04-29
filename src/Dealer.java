import java.util.*;
public class Dealer {
	private ArrayList<Card> deck;
	private ArrayList<Card> hand;
	
	public Dealer() {
		//call reset to get a fresh deck
		
	}
	
	public void reset() {
		deck.clear();//throw away current deck
		emptyHand();//throw away current hand
		//refill a new deck, loop by suit and # of decks
		int numDecks = 3;
		for (int i = 0; i< numDecks; i++) {//add one deck per loop
			CardSuit Suit = CardSuit.SPADES;
			addSuit(Suit);
			Suit = CardSuit.CLUBS;
			addSuit(Suit);
			Suit = CardSuit.HEARTS;
			addSuit(Suit);
			Suit = CardSuit.DIAMONDS;
			addSuit(Suit);
		}

		
	}
	public void shuffle() {
		//shuffle remaining cards
		int x = 0;
		int y = 0;
		Random rand = new Random();
		Card temp = new Card();
		for (int i = 0; i < 50; i++) {//assume deck size is 3 normal decks of 52, 156 total, 50 random swaps in deck
			x = rand.nextInt(156);
			y = rand.nextInt(156);
			temp = deck.get(x);
			deck.set(x, deck.get(y));
			deck.set(y, temp);
		}
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
	public void drawCard() {
		Card card = new Card(0, "placeholder", CardSuit.CLUBS);//should eventually draw from deck
		hand.add(card);
	}
	public void emptyHand() {
		//discard hand
		hand.clear();
	}
	
	private void addSuit(CardSuit Suit) {//helper
		//makes numbered cards 2-10
		
		//makes face cards and aces
	}
	
}
