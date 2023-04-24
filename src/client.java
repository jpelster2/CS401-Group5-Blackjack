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
		Message loginMessage = new Message() //finish adding message after anas completes
		objectOutputStream.writeObject(loginMessage);
		
		loginMessage = (Message)objectInputStream.readObject();
		if(loginMessage.getText().equals("success")) { //finish adding message after anas completes
			JOptionPane.showMessageDialog(null, "Login successful","Login Successful", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(null, "Login failed","Login Error", JOptionPane.ERROR_MESSAGE);
		}
		
		objectInputStream.close();
		objectOutputStream.close();
		socket.close();
	}
}
	
	
