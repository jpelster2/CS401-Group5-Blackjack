package testing;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import blackjack.Card;
import blackjack.CardSuit;

public class CardTest {
    @Test
    public void testGetValue() {
        Card card = new Card(5, "Five of Hearts", CardSuit.HEARTS);
        assertEquals(5, card.getValue());
    }

    @Test
    public void testGetName() {
        Card card = new Card(10, "Ten of Clubs", CardSuit.CLUBS);
        assertEquals("Ten of Clubs", card.getName());
    }

    @Test
    public void testGetSuit() {
        Card card = new Card(11, "Ace of Spades", CardSuit.SPADES);
        assertEquals(CardSuit.SPADES, card.getSuit());
    }

    @Test
    public void testSetAceValue() {
        Card card = new Card(11, "Ace of Diamonds", CardSuit.DIAMONDS);
        card.setAceValue(1);
        assertEquals(1, card.getValue());
    }
}
