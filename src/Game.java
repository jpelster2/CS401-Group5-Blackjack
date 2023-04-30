import java.util.ArrayList;
import java.util.Scanner;

public class Game { 	
	private int id; 
	private int turn; 
	private boolean isActive; 
	private float houseBalance; 
	private float betPool; 
	private ArrayList<Player> gameLobby;
	private Dealer dealer; 
	
	public Game(float houseBalance, ArrayList<Player> gameLobby) {
		setActivity(true);
		setHouseBalance(houseBalance); 
		setGameLobby(gameLobby); 
		Dealer dealer = new Dealer(); 
		setDealer(dealer);
	}
	public Game() {
		setActivity(true);
		Dealer dealer = new Dealer(); 
		setDealer(dealer);
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
	 public void setBetPool (float betPool) {
		this.betPool = betPool;
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
	 public float getBetPool() {
		 return betPool;
	 }
	 public ArrayList<Player> getGameLobby(){
		 return gameLobby;
	 }
	 public Dealer getDealer() {
		 return dealer; 
	 }
	 public void awardPool(ArrayList<Player> gameLobby) {
	// If player beats the dealer they get 1.5 times their bet and playerscore >= dealerscore 
	// Anyone that have the same or lower than the dealer loses
		Dealer dealer = getDealer(); 
		for(int i = 0; i < gameLobby.size(); i++) {
			int playerScore = gameLobby.get(i).currentScore();
			System.out.println(gameLobby.get(i).getUsername() + "'s ending score:" + gameLobby.get(i).currentScore());
			int dealerScore = dealer.currentScore(); 
			if((playerScore > dealerScore) && (playerScore <= 21)) {
				float betAmount = gameLobby.get(i).getCurrentBet(); 
				float winnings = (betAmount/2) + betAmount; 
				gameLobby.get(i).addFunds(winnings);
			}
			else if((dealerScore > playerScore) || (playerScore > 21)) {
				float betAmount = gameLobby.get(i).getCurrentBet(); 
				gameLobby.get(i).removeFunds(betAmount);
			} 
		}
		for(int i =0; i < gameLobby.size(); i++) {
			System.out.println(gameLobby.get(i).getUsername() + "'s current balance:" + gameLobby.get(i).getBalance());
		}
		resetGame(); 
	 }
	 public int getPlayerCount() { 
		 ArrayList<Player> gameLobby = getGameLobby(); 
		 return gameLobby.size(); 
	 }
	 public void collectPool() {
		 //collects the bets from all the players
		 float totalBets = 0;
		 ArrayList<Player> gameLobby = getGameLobby(); 
		 for(int i = 0; i < gameLobby.size(); i++) {
			 Scanner in = new Scanner(System.in); 
			 System.out.println( gameLobby.get(i).getUsername() +  " enter the amount of the bet you would like to place: "); 
			 float betAmount = in.nextFloat();
			 gameLobby.get(i).setCurrentBet(betAmount); 
			 totalBets+=betAmount; 
		}
		 setBetPool(totalBets); 
		 System.out.println(getBetPool());
	 }
	 public void dealCard(Player player) {
		 Card c = new Card();
		 c = dealer.dealCard(); 
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
	public void resetGame() {
		 setId(0); 
		 setTurn(0); 
		 setActivity(false);
		 setHouseBalance(0); 
		 setBetPool(0); 
		 ArrayList<Player> arr = new ArrayList<Player>(); 
		 setGameLobby(arr); 
		 Dealer dealer = new Dealer(); 
		 setDealer(dealer); 
		 setTurn(0); 
	 }
	 public void nextTurn() {
		 ArrayList<Player> gameLobby = getGameLobby(); 
		 int i = getTurn(); 
		if(getTurn() + 1 > gameLobby.size()) {
			setTurn(0);
		}
		else {
			setTurn(i+1); 
		}
	 }
	 public void dealersTurn() {
		 //Add function that will have a dealer turn that will do hut until 17 has been hit 
		 // Anything under 17 the dealer will hit 
		 //above 17 it stops
		 Dealer dealer = getDealer(); 
		 int dealerScore = dealer.currentScore();
		 while(dealerScore <= 17) {
			 System.out.println("Dealer has chosen hit"); 
			 dealer.drawCard();
			 dealerScore = dealer.currentScore();
			 System.out.println("Dealer Score:" + dealer.currentScore()); 
		 }
		 System.out.println("Dealer has chosen stand"); 
		 awardPool(getGameLobby());
	 }
	 public void beginGame(){
		 boolean playGame = true; 
		 Scanner in = new Scanner(System.in);
		 collectPool(); 
		 dealer.drawCard();
		 System.out.println("Dealer's Card: " + dealer.getHand().get(0).getName());
		 while(playGame) {
			 int turn = getTurn(); 
			 if (turn == gameLobby.size()) {
				 dealersTurn();
				 playGame = false; 
			 } else {
				 boolean continueRound = true; 
				 while (continueRound) {
					 System.out.println(gameLobby.get(turn).getUsername() + " Hit or Stand? ");
					 String choice = in.nextLine(); 
					 choice = choice.toUpperCase();
					 int playerScore = gameLobby.get(turn).currentScore();
					 if ((choice.equals("HIT")) && (playerScore < 21)) {
						 dealCard(gameLobby.get(turn)); 
						 System.out.println( gameLobby.get(turn).getUsername() + " has been dealt a card."); 
					 } else if ((choice.equals("STAND")) || playerScore >= 21) {
						 System.out.println( gameLobby.get(turn).getUsername() + " has chosen to stand and end their turn.");
						 continueRound = false; 
					 }
				 }
				 nextTurn(); 
			 }
		 }
		 in.close(); 
	 }
	 
}

