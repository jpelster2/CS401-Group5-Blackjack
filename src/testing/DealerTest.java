package testing;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.Before;
import static org.junit.jupiter.api.Assertions.*;
import blackjack.*;
import java.util.ArrayList;

public class DealerTest {
    private Dealer dealer;
    
    @Before
    public void setUp() {
        dealer = new Dealer();
    }
    
    @Test
    public void testDealerConstructor() {
        assertNotNull(dealer);
        assertEquals(156, dealer.getRemaining());
    }
    
    @Test
    public void testReset() {
        dealer.reset();
        assertEquals(0, dealer.getHand().size());
        assertEquals(0, dealer.getRemaining());
    }
    
    @Test
    public void testShuffle() {
        ArrayList<Card> deckBeforeShuffle = new ArrayList<>(dealer.getDeck());
        dealer.shuffle();
        assertNotEquals(deckBeforeShuffle, dealer.getDeck());
    }
    
    @Test
    public void testDealCard() {
        Card card = dealer.dealCard();
        assertNotNull(card);
        assertEquals(155, dealer.getRemaining());
    }
    
    @Test
    public void testHandTotal() {
        dealer.drawCard();
        dealer.drawCard();
        assertEquals(dealer.getHand().get(0).getValue() + dealer.getHand().get(1).getValue(), dealer.handTotal());
    }
    
    @Test
    public void testDrawCard() {
        int deckSizeBeforeDraw = dealer.getRemaining();
        dealer.drawCard();
        assertEquals(deckSizeBeforeDraw - 1, dealer.getRemaining());
        assertEquals(1, dealer.getHand().size());
    }
    
    @Test
    public void testEmptyHand() {
        dealer.drawCard();
        dealer.emptyHand();
        assertEquals(0, dealer.getHand().size());
    }
    
    @Test
    @RepeatedTest(100)
    public void testCurrentScore() {
    	dealer = new Dealer();
    	
        dealer.drawCard();
        dealer.drawCard();
        dealer.drawCard();
        int score = 0;
        boolean hasAce = false;
        for (Card c : dealer.getHand()) {
        	// Handle aces when we are over 21 points
        	if (c.getValue() == 11) {
        		hasAce = true;
        	}
        	score += c.getValue();
        }
        if (hasAce && score > 21) {
        	score -= 10;
        }
        assertEquals(score, dealer.currentScore());
        dealer.drawCard();
    }
}
