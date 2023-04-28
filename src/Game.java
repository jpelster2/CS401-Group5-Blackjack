
import java.util.ArrayList;
import java.util.Scanner;

public class Game { 	
	private int id; 
	private int turn; 
	private boolean isActive; 
	private float houseBalance; 
	private float betPool; 
	private ArrayList<Player> gameLobby;
	
	public Game(float houseBalance, ArrayList<Player> gameLobby) {
		setActivity(true);
		setHouseBalance(houseBalance); 
		setGameLobby(gameLobby); 
	}
	public Game() {
		setActivity(true);
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
	 public void awardPool(Player user) {
		 //Adjust this after Vinh finished Dealer to 
		 //Loop over all the players in the array list and check if that player one 
		 // To win is playerscore > dealerscore and playerscore<= 21
		float newBalance = getBetPool() + user.getBalance(); 
		user.setBalance(newBalance);
	 }
	 public int getPlayerCount() { 
		 ArrayList<Player> gameLobby = getGameLobby(); 
		 return gameLobby.size(); 
	 }
	 public void collectPool() {
		 //collects the bets from all the players
		 float totalBets = 0;
		 ArrayList<Player> lobby = getGameLobby(); 
		 for(int i = 0; i < lobby.size(); i++) {
			 System.out.println("Enter the amount of the bet you would like to place: "); 
			 Scanner in = new Scanner(System.in); 
			 float betAmount = in.nextFloat();
			 lobby.get(i).setCurrentBet(betAmount); 
			 totalBets+=betAmount; 
			 System.out.print("Current Bet: "); 
			 System.out.println(lobby.get(i).getCurrentBet());
		 }
		 setBetPool(totalBets); 
	 }
	/*
	 public void nextTurn() {
		 //add stuff
	 }
	 public void resetGame() {
		 
	 }
	*/
}

