import java.io.*;
import java.net.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;


public class client {
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

			choice = JOptionPane.showOptionDialog(null,"Select a command", "DVD Collection", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, commands,commands[commands.length - 1]);
			
			switch (choice) {
		 	case 0: doJoinGame1(objectOutputStream, objectInputStream); break;
		 	case 1: doJoinGame2(); break;
		 	case 2: doJoinGame3(); break;
		 	case 3: doJoinGame4(); break;
		 	case 4: Player.getBalance(); break;
		 	case 5: Player.addFunds(); break;
		 	case 6: Player.removeFunds(); break;
		 	case 7: doLogout(objectOutputStream, objectInputStream); break;
		 	default:  // do nothing
		 }
		}while(choice != commands.length-1);
		
	}
	
	public static void doJoinGame1(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		
		try {
			Message joinGameMessage = new Message(MessageType.GAME, "join",null,0); 
			objectOutputStream.writeObject(joinGameMessage); //send to server join game request
		
			joinGameMessage = (Message)objectInputStream.readObject(); //read changed message
			JOptionPane.showMessageDialog(null, "Joined Game 1 ","Join Successful", JOptionPane.INFORMATION_MESSAGE);
			
			new GUI(objectOutputStream, objectInputStream);
			
		
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
}
	
	
