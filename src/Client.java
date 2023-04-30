import java.io.*;
import java.net.*;
import javax.swing.*;

public class Client {
	private static Socket socket;
	private static ObjectInputStream objectInputStream;
	private static ObjectOutputStream objectOutputStream;
	private static boolean inLobbyMenu = true;
	private static String loginUsername;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		String serverAddress = JOptionPane.showInputDialog("Enter the address you want to connect to");
		int serverPort = Integer.parseInt(JOptionPane.showInputDialog("Enter the port you want to connect to"));
		
		// Connect to the ServerSocket at host:port
		socket = new Socket(serverAddress, serverPort);
		JOptionPane.showMessageDialog(null, "Connected to server: "+serverAddress+" on port: "+serverPort, "Connection Status", JOptionPane.INFORMATION_MESSAGE);
		
		// Create object output stream from the output stream to send an object through it
		objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		objectInputStream = new ObjectInputStream(socket.getInputStream());
		
		loginUsername = JOptionPane.showInputDialog("Login with username or cancel to close");
		Message loginMessage = new Message(MessageType.LOGIN, "login",loginUsername,0); 
		objectOutputStream.writeObject(loginMessage); //send to server login request with username
		
		loginMessage = (Message)objectInputStream.readObject(); //read changed message
		if(loginMessage.getText().equals("success")) { 
			JOptionPane.showMessageDialog(null, "Login successful","Login Successful", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(null, "Login failed","Login Error", JOptionPane.ERROR_MESSAGE);
		}
		
		lobby();
	}
	
	public static void lobby() {
		inLobbyMenu = true;
		int choice;
		String[] commands = {"Join Game 1","Join Game 2","Join Game 3","Join game 4","Get Balance","Add Balance","Remove Balance","Logout"};
		do {

			choice = JOptionPane.showOptionDialog(null,"Select a command", "Blackjack Lobby", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, commands,commands[commands.length - 1]);
			
			switch (choice) {
		 	case 0: doJoinGame1(); break;
		 	case 1: doJoinGame2(); break;
		 	case 2: doJoinGame3(); break;
		 	case 3: doJoinGame4(); break;
		 	case 4: doGetBalance(); break;
		 	case 5: doAddFunds(); break;
		 	case 6: doRemoveFunds(); break;
		 	case 7: doLogout(); break;
		 	default:  // do nothing
		 }
		}while(choice != commands.length-1 && inLobbyMenu);
		
	}
	
	public static void doJoinGame1() {
		
		try {
			Message joinGameMessage = new Message(MessageType.LOBBY, "join","1",0); 
			objectOutputStream.writeObject(joinGameMessage); //send to server join game request
		
			joinGameMessage = (Message)objectInputStream.readObject(); //read changed message
			JOptionPane.showMessageDialog(null, "Joined Game 1 ","Join Successful", JOptionPane.INFORMATION_MESSAGE);
			
			inLobbyMenu = false;
			new GUI();
			
		
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void doJoinGame2() {
		
		try {
			Message joinGameMessage = new Message(MessageType.LOBBY, "join","2",0); 
			objectOutputStream.writeObject(joinGameMessage); //send to server join game request
		
			joinGameMessage = (Message)objectInputStream.readObject(); //read changed message
			JOptionPane.showMessageDialog(null, "Joined Game 2 ","Join Successful", JOptionPane.INFORMATION_MESSAGE);
			
			inLobbyMenu = false;
			new GUI();
			
		
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static void doJoinGame3() {
		
		try {
			Message joinGameMessage = new Message(MessageType.LOBBY, "join","3",0); 
			objectOutputStream.writeObject(joinGameMessage); //send to server join game request
		
			joinGameMessage = (Message)objectInputStream.readObject(); //read changed message
			JOptionPane.showMessageDialog(null, "Joined Game 3 ","Join Successful", JOptionPane.INFORMATION_MESSAGE);
			
			inLobbyMenu = false;
			new GUI();
			
		
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void doJoinGame4() {
		
		try {
			Message joinGameMessage = new Message(MessageType.LOBBY, "join","4",0); 
			objectOutputStream.writeObject(joinGameMessage); //send to server join game request
		
			joinGameMessage = (Message)objectInputStream.readObject(); //read changed message
			JOptionPane.showMessageDialog(null, "Joined Game 4 ","Join Successful", JOptionPane.INFORMATION_MESSAGE);
			
			inLobbyMenu = false;
			new GUI();
			
		
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void doLeaveGame() {
		
		try {
			Message leaveGameMessage = new Message(MessageType.LOBBY, "leave","4",0); 
			objectOutputStream.writeObject(leaveGameMessage); //send to server join game request
		
			leaveGameMessage = (Message)objectInputStream.readObject(); //read changed message
			JOptionPane.showMessageDialog(null, "Left the Game ","Leave Successful", JOptionPane.INFORMATION_MESSAGE);
			
		
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void doAddFunds() {
		
		try {
			String addBalance = JOptionPane.showInputDialog("Enter the amount to add: ");
			Message balanceMessage = new Message(MessageType.BALANCE, "add",addBalance,0); //sends message that has a balance type with add action and amount to add
			objectOutputStream.writeObject(balanceMessage); //send to server logout request
		
			balanceMessage = (Message)objectInputStream.readObject();//read changed message
			JOptionPane.showMessageDialog(null, "User Balance updated to: "+balanceMessage.getText(),"Balance Update", JOptionPane.INFORMATION_MESSAGE);
		
		} catch (ClassNotFoundException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} 
	}
	
	public static void doRemoveFunds() {
		
		try {
			String removeBalance = JOptionPane.showInputDialog("Enter the amount to remove: ");
			Message balanceMessage = new Message(MessageType.BALANCE, "remove",removeBalance,0); //sends message that has a balance type with add action and amount to remove
			objectOutputStream.writeObject(balanceMessage); //send to server logout request
		
			balanceMessage = (Message)objectInputStream.readObject();//read changed message
			
			if(balanceMessage.getAction().toString().equals("error")){
				JOptionPane.showMessageDialog(null, "Cannot go to a negative balance. Nothing was done","Error Message", JOptionPane.ERROR_MESSAGE);
			}
			else {
			JOptionPane.showMessageDialog(null, "User Balance updated to: "+balanceMessage.getText(),"Balance Update", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (ClassNotFoundException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} 
	}
	
	public static void doGetBalance() {
		
		try {
			Message balanceMessage = new Message(MessageType.BALANCE, "request",null,0);
			objectOutputStream.writeObject(balanceMessage); //send to server logout request
		
			balanceMessage = (Message)objectInputStream.readObject();//read changed message
			JOptionPane.showMessageDialog(null, "User Balance: "+balanceMessage.getText(),"Balance Inquiry", JOptionPane.INFORMATION_MESSAGE);
		
		} catch (ClassNotFoundException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} 
	}
	
	public static void doLogout() {
		
		try {
			Message logoutMessage = new Message(MessageType.LOGIN, "logout",null,0);
			objectOutputStream.writeObject(logoutMessage); //send to server logout request
		
			logoutMessage = (Message)objectInputStream.readObject();//read changed message
			JOptionPane.showMessageDialog(null, "Logout Status: "+logoutMessage.getText(),"Logout Successful", JOptionPane.INFORMATION_MESSAGE);
		
			objectInputStream.close();
			objectOutputStream.close();
			socket.close();
			System.exit(0);
		} catch (ClassNotFoundException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	public static String doHit() {
		
		try{
			Message hitMessage = new Message(MessageType.GAME, "hit" , null, 0);
			objectOutputStream.writeObject(hitMessage);
		
			hitMessage = (Message)objectInputStream.readObject();//read changed message
		
			return hitMessage.getText();
		
		}catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		
	}
	
	public static String doStand() {
		
		try{
			Message standMessage = new Message(MessageType.GAME, "stand" , null, 0);
			objectOutputStream.writeObject(standMessage);
		
			standMessage = (Message)objectInputStream.readObject();//read changed message
		
			return standMessage.getText(); //username of next player
		
		}catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		
	}
	
	public static String doGameState() {
		
		try{
			Message gameMessage = new Message(MessageType.GAME, "status" , null, 0);
			objectOutputStream.writeObject(gameMessage); //send to server logout request
		
			gameMessage = (Message)objectInputStream.readObject();//read changed message
		
			return gameMessage.getText(); //the message that game has will need to be the dealers current hand at beginning of round 
			//for example: 10 of hearts
		
		}catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		
	}
	
	public static String doWhosTurn() {
		Message gameMessage = new Message(MessageType.GAME, "status" , loginUsername, 0);
		try{
			
			while(!gameMessage.getAction().equals("go")) { //while its not current players turn
				
				try {
					Thread.sleep(200);	//sleep for 200ms before sending message
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				objectOutputStream.writeObject(gameMessage); //send to server status request
				
				gameMessage = (Message)objectInputStream.readObject();//read changed message
				
				if(gameMessage.getAction().equals("dealer")) {	//when its dealers action return the text of the all dealers card
					return gameMessage.getText();
				}
				if(gameMessage.getText().startsWith("username: ")) { 	//when a players status message is sent it will update the status label in gui
					return gameMessage.getText();
				}
			}
		
			return gameMessage.getAction(); //when the go action is called for player return the text will be the players username and will add that to status label in gui

		
		}catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		
	}
	
	public static void doWinnings() {
		
		try{
			Message gameMessage = new Message(MessageType.GAME, "winnings" , loginUsername, 0);
			objectOutputStream.writeObject(gameMessage); //send to server logout request
		
			gameMessage = (Message)objectInputStream.readObject();//read changed message
			
			if(!gameMessage.getText().equals("0")) {
				Message balanceMessage = new Message(MessageType.BALANCE, "add",gameMessage.getText(),0); //sends message that has a balance type with add action and amount to add
				objectOutputStream.writeObject(balanceMessage); //send to server logout request
			
				balanceMessage = (Message)objectInputStream.readObject();//read changed message
				JOptionPane.showMessageDialog(null, "User Balance updated to: "+balanceMessage.getText(),"Balance Update", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "You lose :(","Balance Update", JOptionPane.INFORMATION_MESSAGE);
			}
		
		
		}catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public static void dobet(String betBalance) {
		
		try{
			Message gameMessage = new Message(MessageType.GAME, "bet" , betBalance, 0);
			objectOutputStream.writeObject(gameMessage); //send to server bet request
		
			gameMessage = (Message)objectInputStream.readObject();//read changed message
			if(gameMessage.getText().equals("success")) {
				JOptionPane.showMessageDialog(null, "Bet placed","Bet Update", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "You are too broke to do that","Bet Update", JOptionPane.ERROR_MESSAGE);
			}
		
		}catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
}
	
	
