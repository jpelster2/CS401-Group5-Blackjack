package blackjack;

public enum CardSuit {
	SPADES,
	CLUBS,
	HEARTS,
	DIAMONDS,
	;
	@Override
	public String toString() {//for using stringbuilder with card names
	    switch (this) {
	        case SPADES:
	            return "Spades";
	        case CLUBS:
	            return "Clubs";
	        case HEARTS:
	            return "Hearts";
	        case DIAMONDS:
	            return "Diamonds";
	        default:
	            return null;
	    }
	}
}
