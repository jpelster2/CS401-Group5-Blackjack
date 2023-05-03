package testing;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import blackjack.*;

public class PlayerTest {

    @Test
    public void testAddCardToHand() {
        Player p = new Player("Charlie", "password", 100);
        Card c = new Card();
        p.addCardToHand(c);
        assertTrue(p.getHand().contains(c));
    }
    
    @Test
    public void testRemoveCardFromHand() {
        Player p = new Player("player1", "password", 100);
        Card c = new Card();
        p.addCardToHand(c);
        p.getHand().remove(c);
        assertFalse(p.getHand().contains(c));
    }
    
    @Test
    public void testAddFunds() {
        Player p = new Player("Alice", "password", 100);
        p.addFunds(50);
        assertEquals(150, p.getBalance(), 0.01);
    }
    
    @Test
    public void testRemoveFunds() {
        Player p = new Player("Bob", "password", 100);
        p.removeFunds(50);
        assertEquals(50, p.getBalance(), 0.01);
    }
    
    @Test
    public void testSetCurrentBet() {
        Player p = new Player("player1", "password", 100);
        p.setCurrentBet(50);
        assertEquals(50, p.getCurrentBet(), 0.01);
    }
    
    @Test
    public void testCurrentScore() {
        Player p = new Player("player1", "password", 100);
        Card c1 = new Card(11, "Ace of Clubs", CardSuit.CLUBS);
        Card c2 = new Card(10, "King of Clubs", CardSuit.CLUBS);
        p.addCardToHand(c1);
        p.addCardToHand(c2);
        assertEquals(21, p.currentScore());
    }
    
}
