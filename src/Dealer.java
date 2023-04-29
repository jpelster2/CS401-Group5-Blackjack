import java.util.*;
public class Dealer {
	private ArrayList<Card> deck;
	private ArrayList<Card> hand;
	
	public Dealer() {
		reset();//call reset to get a fresh deck
		
	}
	
	public void reset() {
		deck.clear();//throw away current deck
		emptyHand();//throw away current hand
		//refill a new deck, loop by # of decks
		int numDecks = 3;
		for (int i = 0; i< numDecks; i++) {//add one deck per loop
			CardSuit Suit = CardSuit.SPADES;//add cards by suit
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
			x = rand.nextInt(deck.size());//random index 0-155, now dynamic for cards to be drawn
			y = rand.nextInt(deck.size());
			temp = deck.get(x);
			deck.set(x, deck.get(y));
			deck.set(y, temp);
		}
	}
	public Card dealCard() {
		Card card = new Card();
		card = deck.get((0));//get card value
		deck.remove(0);//remove card from top of deck
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
		Card card = new Card();
		card = deck.get((0));//get card value
		deck.remove(0);//remove card from top of deck
		hand.add(card);
	}
	public void emptyHand() {
		//discard hand
		hand.clear();
	}
	
	private void addSuit(CardSuit Suit) {//helper
		//makes numbered cards 2-10
		Card card = new Card();
		StringBuilder str = new StringBuilder();
		for (int i = 2; i < 11; i++) {
			str.setLength(0);//clear str
			str.append(i);
			str.append(" of ");
			str.append(Suit.toString());
			card = new Card(i, str.toString(), Suit);
			deck.add(card);
		}
		//makes face cards and aces
		str.setLength(0);//clear str
		str.append("Ace of ");
		str.append(Suit.toString());
		card = new Card(1, str.toString(), Suit);
		deck.add(card);
		str.setLength(0);//clear str
		str.append("Jack of ");
		str.append(Suit.toString());
		card = new Card(10, str.toString(), Suit);
		deck.add(card);
		str.setLength(0);//clear str
		str.append("Queen of ");
		str.append(Suit.toString());
		card = new Card(10, str.toString(), Suit);
		deck.add(card);
		str.setLength(0);//clear str
		str.append("King of ");
		str.append(Suit.toString());
		card = new Card(10, str.toString(), Suit);
		deck.add(card);
	}
	
}
