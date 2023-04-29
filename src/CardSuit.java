
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
	            return "SPADES";
	        case CLUBS:
	            return "CLUBS";
	        case HEARTS:
	            return "HEARTS";
	        case DIAMONDS:
	            return "DIAMONDS";
	        default:
	            return null;
	    }
	}
}
