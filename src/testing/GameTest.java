package testing;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.jupiter.api.Assertions.*;
import blackjack.*;
import java.util.ArrayList;

public class GameTest {
	
	Game game;
	Player player1, player2;
	
	@Before
	public void setUp() throws Exception {
		game = new Game();
		player1 = new Player("1", "player1", 1000);
		player2 = new Player("2", "player2", 2000);
		game.getGameLobby().add(player1);
		game.getGameLobby().add(player2);
	}
	
	@After
	public void tearDown() throws Exception {
		game = null;
		player1 = null;
		player2 = null;
	}
	
	@Test
	public void testGetPlayerCount() {
		assertEquals(2, game.getPlayerCount());
	}
	
	@Test
	public void testDealCard() {
		game.dealCard(player1);
		assertEquals(1, player1.getHand().size());
	}
	
	@Test
	public void testPlayersCards() {
		game.dealCard(player1);
		game.dealCard(player1);
		ArrayList<Card> cards = game.playersCards(player1);
		assertEquals(2, cards.size());
	}
	
	@Test
	public void testBeginGame() {
		assertFalse(game.getActivity());
		game.beginGame();
		assertTrue(game.getActivity());
	}
	
	@Test
	public void testNextTurn() {
		game.nextTurn();
		assertEquals(1, game.getTurn());
	}
	
	@Test
	public void testAwardPool() {
		player1.setCurrentBet(100);
		player2.setCurrentBet(200);
		Dealer dealer = game.getDealer();
		dealer.getHand().add(new Card(10, "Diamonds", null));
		dealer.getHand().add(new Card(9, "Hearts", null));
		player1.getHand().add(new Card(10, "Spades", null));
		player1.getHand().add(new Card(10, "Diamonds", null));
		player2.getHand().add(new Card(5, "Clubs", null));
		player2.getHand().add(new Card(10, "Hearts", null));
		game.awardPool();
		assertEquals(1150, player1.getBalance(), 0.01);
		assertEquals(1800, player2.getBalance(), 0.01);
	}
}
