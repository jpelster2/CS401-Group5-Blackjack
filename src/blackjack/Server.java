package blackjack;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Random;

// This class based on, but not copied verbatim from,
// the "Object Over Socket Networking" and "Threaded Networking" examples,
// as well as Assignment 5. This file created by James Pelster on 04/24/2023.
public class Server {
	public static ArrayList<Game> gameList;
	
	public static void main(String[] args){
		ServerSocket serverSocket = null;
		gameList = new ArrayList<Game>();
		
		// Initialize the game instances, we have 4 tables.
		for (int i = 0; i < 4; ++i) {
			gameList.add(new Game());
		}
		
		try {
			// Create a server socket on port 5555, that can be reused for multiple connections
			serverSocket = new ServerSocket(5555);
			serverSocket.setReuseAddress(true);
			System.out.println("Server socket open, awaiting connections.");
			
			// Listen for incoming connections
			while (true) {
				// Create socket to receive the incoming client
				Socket client = serverSocket.accept();
				
				// Print when a new connection comes in
				System.out.println("New client connected from " + client.getInetAddress().getHostAddress());
				
				// Create a new thread to handle this client socket
				new Thread(new ClientHandler(client)).start();
			}
			
		} catch (IOException ex) {
			System.out.println("An IOException occurred: " + ex.getLocalizedMessage());
			ex.printStackTrace();
			
		} finally {
			// Try to clean up before exiting
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	private static class ClientHandler implements Runnable {
		private enum ClientStatus {
			NOT_LOGGED_IN,
			IN_LOBBY,
			IN_GAME
		}
		
		public String username;
		private final Socket clientSocket;
		private ClientStatus status;
		private int tableNum;
		private float balance;
		private Player player;
		
		public ClientHandler(Socket socket) {
			clientSocket = socket;
			status = ClientStatus.NOT_LOGGED_IN;
			tableNum = -1;
			balance = 100;
			username = "";
			player = null;
		}
		
		public void run() {
			ObjectOutputStream out = null;
			ObjectInputStream in = null;
			
			try {
				// Get the outputStream and inputStream of the client
				out = new ObjectOutputStream(clientSocket.getOutputStream());
				in = new ObjectInputStream(clientSocket.getInputStream());
				
				// TODO: Loop infinitely waiting for messages
				boolean exit = false;
				while (!exit) {
					Message msg = (Message)in.readObject();
					Message reply = null;
					
					MessageType type = msg.getType();
					String action = msg.getAction();
					String text = msg.getText();
					switch (type) {
					case LOGIN:
						if (action.equals("login")) {
							reply = new Message(MessageType.LOGIN, "login", "success", 0);
							status = ClientStatus.IN_LOBBY;
							username = msg.getText();
							player = new Player(username, "", balance);
							player.setId(username);
							System.out.println("User \""+username+"\" logged in.");
						} else if (action.equals("logout")) {
							reply = new Message(MessageType.LOGIN, "logout", "success", 0);
							status = ClientStatus.NOT_LOGGED_IN;
							exit = true;
							System.out.println("User \""+username+"\" logged out. Disconnecting client...");
						}
						break;
					case BALANCE:
						if (action.equals("request")) {
							reply = new Message(MessageType.BALANCE, "amount", Float.toString(balance), 0);
							System.out.println("User \""+username+"\" requested their balance info.");
						} else if (action.equals("add")) {
							balance += Float.valueOf(text);
							player.setBalance(balance);
							reply = new Message(MessageType.BALANCE, "amount", Float.toString(balance), 0);
							System.out.println("User \""+username+"\" added $"+text+" to their balance.");
						} else if (action.equals("remove")) {
							if (balance - Float.valueOf(text) < 0) {
								reply = new Message(MessageType.BALANCE, "error", Float.toString(balance), 0);
								System.out.println("User \""+username+"\" tried to subtract $"+text+" from their balance, but didn't have enough money.");
							} else {
								balance -= Float.valueOf(text);
								player.setBalance(balance);
								reply = new Message(MessageType.BALANCE, "amount", Float.toString(balance), 0);
								System.out.println("User \""+username+"\" subtracted $"+text+" from their balance.");
							}
						}
						break;
					case GAME:
						Game thisGame = gameList.get(tableNum);
						if (action.equals("hit")) {
							// Deal this player a card.
							thisGame.dealCard(player);
							// Get the last card in their hand, which should be the most recently-added.
							Card newCard = player.getHand().get(player.getHand().size() - 1);
							if (player.currentScore() > 21)
								reply = new Message(MessageType.GAME, "card", "busted", 0);
							else
								reply = new Message(MessageType.GAME, "card", newCard.getName(), 0);
							System.out.println("Player \""+username+"\" hit, gaining new card "+newCard.getName());
						} else if (action.equals("stand")) {
							// Increment turn number
							thisGame.nextTurn();
							// Check whose turn it is now and tell the client.
							if (thisGame.getTurn() == thisGame.getGameLobby().size()) {
								//Build a string that is made of all the cards in their hand, separated by commas.
								ArrayList<Card> hand = thisGame.getDealer().getHand();
								StringBuilder sb = new StringBuilder();
								for (int i = 0; i < hand.size(); ++i) {
									Card c = hand.get(i);
									sb.append(c.getName());
									if ((i + 1) < hand.size()) {
										sb.append(", ");
									}
								}
								reply = new Message(MessageType.GAME, "dealer", sb.toString(), 0);
							// Otherwise, check if it's this client's turn, or someone else's.
							} else {
								String activePlayerName = thisGame.getGameLobby().get(thisGame.getTurn()).getUsername();
								reply = new Message(MessageType.GAME, "stand", activePlayerName, 0);
							}
						} else if (action.equals("start")) {
							thisGame.beginGame();
							String dealersCard = thisGame.getDealer().getHand().get(0).getName();
							reply = new Message(MessageType.GAME, "status", dealersCard, 0);
						} else if (action.equals("status")) {
							// If all the players have gone, it's the dealer's turn, tell the clients what
							// the dealer's final hand is.
							if (thisGame.getTurn() == thisGame.getGameLobby().size()) {
								//Build a string that is made of all the cards in their hand, separated by commas.
								ArrayList<Card> hand = thisGame.getDealer().getHand();
								StringBuilder sb = new StringBuilder();
								for (int i = 0; i < hand.size(); ++i) {
									Card c = hand.get(i);
									sb.append(c.getName());
									if ((i + 1) < hand.size()) {
										sb.append(", ");
									}
								}
								reply = new Message(MessageType.GAME, "dealer", sb.toString(), 0);
							// Otherwise, check if it's this client's turn, or someone else's.
							} else {
								String activePlayerName = thisGame.getGameLobby().get(thisGame.getTurn()).getUsername();
								if (activePlayerName.equals(username)) {
									// If it's our turn, tell that client to allow hitting or staying.
									reply = new Message(MessageType.GAME, "go", "", 0);
								} else {
									// Otherwise, just tell them whose turn it is.
									reply = new Message(MessageType.GAME, "status", activePlayerName, 0);
								}
							}
						} else if (action.equals("bet")) {
							float amount = Float.valueOf(text);
							if (balance - amount < 0) {
								reply = new Message(MessageType.GAME, "bet", "too_broke", 0);
							} else {
								player.setCurrentBet(amount);
								reply = new Message(MessageType.GAME, "bet", "success", 0);
							}
						} else if (action.equals("winnings")) {
							if (player.getBalance() > balance) {
								reply = new Message(MessageType.GAME, "balance", Float.toString(player.getBalance()), 0);
							} else {
								reply = new Message(MessageType.GAME, "balance", "0", 0);
							}
							balance = player.getBalance();
						}
						break;
					case LOBBY:
						// Join action
						if (action.equals("join") && status == ClientStatus.IN_LOBBY) {
							int tableToJoin = Integer.valueOf(text) - 1;
							// Check to make sure player is only joining a valid table.
							if (tableToJoin >= 0 && tableToJoin < gameList.size()) {
								tableNum = tableToJoin;
								status = ClientStatus.IN_GAME;
								gameList.get(tableToJoin).getGameLobby().add(player);
								reply = new Message(MessageType.LOBBY, "success", null, 0);
								System.out.println("User \""+username+"\" joined game table "+text);
							// Fail if they try to join a nonexistent table.
							} else {
								reply = new Message(MessageType.LOBBY, "error", null, 0);
							}
						// Leave action
						} else if (action.equals("leave") && status == ClientStatus.IN_GAME) {
							gameList.get(tableNum).getGameLobby().remove(player);
							tableNum = -1;
							status = ClientStatus.IN_LOBBY;
							reply = new Message(MessageType.LOBBY, "success", null, 0);
							System.out.println("User \""+username+"\" left their game table.");
						}
						break;
					}
					
					if (reply != null) {
						out.writeObject(reply);
					}
				}
				
			} catch (IOException ex) {
				ex.printStackTrace();
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				
			} finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
					}
					clientSocket.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
