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
			int dealerScore = dealer.currentScore(); 
			if((playerScore > dealerScore) && (playerScore >= 21)) {
				float betAmount = gameLobby.get(i).getCurrentBet(); 
				float winnings = (betAmount/2) + betAmount; 
				gameLobby.get(i).addFunds(winnings);
			}
			else if(dealerScore > playerScore) {
				float betAmount = gameLobby.get(i).getCurrentBet(); 
				gameLobby.get(i).removeFunds(betAmount);
			}
		}
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
			 System.out.println("Enter the amount of the bet you would like to place: "); 
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
	 }
	 public void nextTurn() {
		 Scanner in = new Scanner(System.in); 
		 for(int i = 0; i < gameLobby.size(); i++) {
			 System.out.println(gameLobby.get(i).getUsername() + " Hit or Stand"); 
			 String choice = in.nextLine(); 
			 choice = choice.toUpperCase();
			 if(choice.equals("HIT")) {
				 dealCard(gameLobby.get(i)); 
				 System.out.println( gameLobby.get(i).getUsername() + " has been dealt a card."); 
			 }else if (choice.equals("STAND")) {
				 System.out.println( gameLobby.get(i).getUsername() + " has chosen to stand and end their turn.");
			 }
		 }
		 in.close(); 
	 }
	 //Add function to check for blackjack specifically, this will be called after the cards are initally dealed
	 // Add function maybe for dealers turn
}

