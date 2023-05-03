package blackjack;
import java.util.*;

public class Dealer {
	private ArrayList<Card> deck;
	private ArrayList<Card> hand;
	
	public Dealer() {
		this.deck = new ArrayList<Card>(); 
		this.hand = new ArrayList<Card>();
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
		shuffle(); 
	}
	
	public void reset() {
		this.deck.clear();//throw away current deck
		emptyHand();//throw away current hand
	}
	
	public void shuffle() {
		//shuffle remaining cards
		int x = 0;
		int y = 0;
		Random rand = new Random();
		Card temp = new Card();
		for (int i = 0; i < 50; i++) {//assume deck size is 3 normal decks of 52, 156 total, 50 random swaps in deck
			x = rand.nextInt(this.deck.size());//random index 0-155, now dynamic for cards to be drawn
			y = rand.nextInt(this.deck.size());
			temp = this.deck.get(x);
			this.deck.set(x, deck.get(y));
			this.deck.set(y, temp);
		}
	}
	public Card dealCard() {
		Card card = new Card();
		card = this.deck.get((0));//get card value
		this.deck.remove(0);//remove card from top of deck
		return card;
	}
	public int getRemaining() {//deck size
		return this.deck.size();
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
		card = this.deck.get((0));//get card value
		this.deck.remove(0);//remove card from top of deck
		this.hand.add(card);
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
			this.deck.add(card);
		}
		//makes face cards and aces
		str.setLength(0);//clear str
		str.append("Ace of ");
		str.append(Suit.toString());
		card = new Card(11, str.toString(), Suit);
		this.deck.add(card);
		str.setLength(0);//clear str
		str.append("Jack of ");
		str.append(Suit.toString());
		card = new Card(10, str.toString(), Suit);
		this.deck.add(card);
		str.setLength(0);//clear str
		str.append("Queen of ");
		str.append(Suit.toString());
		card = new Card(10, str.toString(), Suit);
		this.deck.add(card);
		str.setLength(0);//clear str
		str.append("King of ");
		str.append(Suit.toString());
		card = new Card(10, str.toString(), Suit);
		this.deck.add(card);
	}
	public ArrayList<Card> getHand(){
		return hand;
	}
	public ArrayList<Card> getDeck() {
		return deck;
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