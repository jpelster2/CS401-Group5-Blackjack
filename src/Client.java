import java.io.*;
import java.net.*;
import javax.swing.*;


public class Client {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		String serverAddress = JOptionPane.showInputDialog("Enter the address you want to connect to");
		int serverPort = Integer.parseInt(JOptionPane.showInputDialog("Enter the port you want to connect to"));
		
		// Connect to the ServerSocket at host:port
		Socket socket = new Socket(serverAddress, serverPort);
		JOptionPane.showMessageDialog(null, "Connected to server: "+serverAddress+" on port: "+serverPort, "Connection Status", JOptionPane.INFORMATION_MESSAGE);
		
		// Create object output stream from the output stream to send an object through it
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
		
		String loginUsername = JOptionPane.showInputDialog("Login with username or cancel to close");
		Message loginMessage = new Message(MessageType.LOGIN, "login",loginUsername,0); 
		objectOutputStream.writeObject(loginMessage); //send to server login request with username
		
		loginMessage = (Message)objectInputStream.readObject(); //read changed message
		if(loginMessage.getText().equals("success")) { 
			JOptionPane.showMessageDialog(null, "Login successful","Login Successful", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(null, "Login failed","Login Error", JOptionPane.ERROR_MESSAGE);
		}
		
		lobby(objectOutputStream, objectInputStream);
		
		objectInputStream.close();
		objectOutputStream.close();
		socket.close();
		System.exit(0);
	}
	
	public static void lobby(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		
		int choice;
		String[] commands = {"Join Game 1","Join Game 2","Join Game 3","Join game 4","Get Balance","Add Balance","Remove Balance","Logout"};
		do {

			choice = JOptionPane.showOptionDialog(null,"Select a command", "Blackjack Lobby", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, commands,commands[commands.length - 1]);
			
			switch (choice) {
		 	case 0: doJoinGame1(objectOutputStream, objectInputStream); break;
		 	case 1: doJoinGame2(objectOutputStream, objectInputStream); break;
		 	case 2: doJoinGame3(objectOutputStream, objectInputStream); break;
		 	case 3: doJoinGame4(objectOutputStream, objectInputStream); break;
		 	case 4: doGetBalance(objectOutputStream, objectInputStream); break;
		 	case 5: doAddFunds(objectOutputStream, objectInputStream); break;
		 	case 6: doRemoveFunds(objectOutputStream, objectInputStream); break;
		 	case 7: doLogout(objectOutputStream, objectInputStream); break;
		 	default:  // do nothing
		 }
		}while(choice != commands.length-1);
		
	}
	
	public static void doJoinGame1(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		
		try {
			Message joinGameMessage = new Message(MessageType.LOBBY, "join","1",0); 
			objectOutputStream.writeObject(joinGameMessage); //send to server join game request
		
			joinGameMessage = (Message)objectInputStream.readObject(); //read changed message
			JOptionPane.showMessageDialog(null, "Joined Game 1 ","Join Successful", JOptionPane.INFORMATION_MESSAGE);
			
			new GUI(objectOutputStream, objectInputStream);
			
		
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void doJoinGame2(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		
		try {
			Message joinGameMessage = new Message(MessageType.LOBBY, "join","2",0); 
			objectOutputStream.writeObject(joinGameMessage); //send to server join game request
		
			joinGameMessage = (Message)objectInputStream.readObject(); //read changed message
			JOptionPane.showMessageDialog(null, "Joined Game 2 ","Join Successful", JOptionPane.INFORMATION_MESSAGE);
			
			new GUI(objectOutputStream, objectInputStream);
			
		
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static void doJoinGame3(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		
		try {
			Message joinGameMessage = new Message(MessageType.LOBBY, "join","3",0); 
			objectOutputStream.writeObject(joinGameMessage); //send to server join game request
		
			joinGameMessage = (Message)objectInputStream.readObject(); //read changed message
			JOptionPane.showMessageDialog(null, "Joined Game 3 ","Join Successful", JOptionPane.INFORMATION_MESSAGE);
			
			new GUI(objectOutputStream, objectInputStream);
			
		
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void doJoinGame4(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		
		try {
			Message joinGameMessage = new Message(MessageType.LOBBY, "join","4",0); 
			objectOutputStream.writeObject(joinGameMessage); //send to server join game request
		
			joinGameMessage = (Message)objectInputStream.readObject(); //read changed message
			JOptionPane.showMessageDialog(null, "Joined Game 4 ","Join Successful", JOptionPane.INFORMATION_MESSAGE);
			
			new GUI(objectOutputStream, objectInputStream);
			
		
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void doAddFunds(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		
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
	
	public static void doRemoveFunds(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		
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
	
	public static void doGetBalance(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		
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
	
	public static void doLogout(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		
		try {
			Message logoutMessage = new Message(MessageType.LOGIN, "logout",null,0);
			objectOutputStream.writeObject(logoutMessage); //send to server logout request
		
			logoutMessage = (Message)objectInputStream.readObject();//read changed message
			JOptionPane.showMessageDialog(null, "Logout Status: "+logoutMessage.getText(),"Logout Successful", JOptionPane.INFORMATION_MESSAGE);
		
			objectInputStream.close();
			objectOutputStream.close();
			System.exit(0);
		} catch (ClassNotFoundException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} 
	}
	
	public static String doHit(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		
		try{
			Message hitMessage = new Message(MessageType.GAME, "hit" , null, 0);
			objectOutputStream.writeObject(hitMessage); //send to server logout request
		
			hitMessage = (Message)objectInputStream.readObject();//read changed message
		
			return hitMessage.getText();
		
		}catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		
	}
	
	public static String doStand(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		
		try{
			Message standMessage = new Message(MessageType.GAME, "stand" , null, 0);
			objectOutputStream.writeObject(standMessage); //send to server logout request
		
			standMessage = (Message)objectInputStream.readObject();//read changed message
		
			return standMessage.getText(); //the message that stand has will need to be the dealers full deck after and hits it makes 
			//for example: 10 of hearts, queen(10) of spades, 1 of diamonds
		
		}catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		
	}
	
	public static String doGameState(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		
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
	
	
}
	
	
