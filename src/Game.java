import java.util.ArrayList;
import java.util.Scanner;

public class Game { 	
	private int id; 
	private int turn; 
	private boolean isActive; 
	private float houseBalance;
	private ArrayList<Player> gameLobby;
	private Dealer dealer; 
	
	public Game() {
		setActivity(true);
		setHouseBalance(100000000);
		dealer = new Dealer();
		gameLobby = new ArrayList<Player>();
		isActive = false;
	}
	 public void setId(int id) {
		this.id = id; 
	}
	 public void setTurn(int turn) {
		this.turn = turn;
	}
	 public void setActivity(boolean activity) {
		this.isActive = activity;
	}
	 public void setHouseBalance(float houseBalance) {
		this.houseBalance = houseBalance;
	}
	 public void setGameLobby(ArrayList<Player> gameLobby) {
		 this.gameLobby = gameLobby; 
	 }
	 public void setDealer(Dealer dealer) {
		 this.dealer = dealer; 
	 }
	 public int getId(){
		 return id; 
	 }
	 public int getTurn() {
		 return turn;
	 }
	 public boolean getActivity() {
		 return isActive; 
	 }
	 public float getHouseBalance() {
		 return houseBalance;
	 }
	 public ArrayList<Player> getGameLobby(){
		 return gameLobby;
	 }
	 public Dealer getDealer() {
		 return dealer; 
	 }
	 public void awardPool() {
		 // If player beats the dealer they get 1.5 times their bet and playerscore >= dealerscore 
		 // Anyone that have the same or lower than the dealer loses
		 for(Player p : gameLobby) {
			int playerScore = p.currentScore();
			System.out.println(p.getUsername() + "'s ending score:" + p.currentScore());
			int dealerScore = dealer.currentScore();
			if (dealerScore > 21)
				System.out.println("Dealer busted!");
			// Player won?
			if ((playerScore > dealerScore || dealerScore > 21) && (playerScore <= 21)) {
				float betAmount = p.getCurrentBet(); 
				float winnings = (betAmount/2) + betAmount; 
				p.addFunds(winnings);
				System.out.println(p.getUsername() + " won!");
			// Player lost or busted?
			} else if ((dealerScore >= playerScore) || (playerScore > 21)) {
				float betAmount = p.getCurrentBet(); 
				p.removeFunds(betAmount);
				
				if (playerScore > 21)
					System.out.println(p.getUsername() + " busted!");
				else
					System.out.println(p.getUsername() + " lost!");
			}
			p.emptyHand();
		}
		for(int i =0; i < gameLobby.size(); i++) {
			System.out.println(gameLobby.get(i).getUsername() + "'s current balance:" + gameLobby.get(i).getBalance());
		}
	 }
	 public int getPlayerCount() {
		 return gameLobby.size(); 
	 }
	 public void dealCard(Player player) {
		 Card c = dealer.dealCard(); 
		 player.addCardToHand(c);
	 }
	public ArrayList<Card> playersCards(Player player){
		ArrayList<Card> hand = new ArrayList<Card>(); 
		hand = player.getHand(); 
		if(hand.size() == 0) {
			System.out.println("Player " + player.getUsername() + " has 0 cards.");
		}
		for(int i =0; i < hand.size(); i++) {
			System.out.println(hand.get(i).getName()); 
		}
		return hand;
	}
	
	public void dealersTurn() {
		 //Add function that will have a dealer turn that will do hut until 17 has been hit 
		 // Anything under 17 the dealer will hit 
		 // above or equal to 17 it stops.
		 int dealerScore = dealer.currentScore();
		 while (dealerScore < 17) {
			 System.out.println("Dealer has chosen hit"); 
			 dealer.drawCard();
			 dealerScore = dealer.currentScore();
			 System.out.println("Dealer Score:" + dealer.currentScore()); 
		 }
		 System.out.println("Dealer has chosen stand"); 
		 awardPool();
		 isActive = false;
	}
	
	public void beginGame() {
		if (!isActive) {
			setTurn(0);
			setHouseBalance(1000000);
			// Draw the dealer's first card
			dealer.emptyHand();
			dealer.drawCard();
			// Set the game to active so trying to reset/begin it won't destroy an in-progress session
			isActive = true;
		}
	}
	
	public void nextTurn() {
		turn += 1;
		
		// If our turn has just gone past the end of our players, then
		// it is now the dealer's turn, and the end of the game.
		if (turn == gameLobby.size()) {
			dealersTurn();
		}
	}
}

