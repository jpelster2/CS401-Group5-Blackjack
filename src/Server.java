import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;

// This class based on, but not copied verbatim from,
// the "Object Over Socket Networking" and "Threaded Networking" examples,
// as well as Assignment 5. This file created by James Pelster on 04/24/2023.
public class Server {
	//ArrayList<Game> gameList;
	
	public static void main(String[] args){
		ServerSocket serverSocket = null;
		//gameList = new ArrayList<Game>();
		
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
		private int balance;
		
		public ClientHandler(Socket socket) {
			clientSocket = socket;
			status = ClientStatus.NOT_LOGGED_IN;
			tableNum = -1;
			balance = 100;
			username = "";
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
					switch (type) {
					case LOGIN:
						if (msg.getAction().equals("login")) {
							reply = new Message(MessageType.LOGIN, "login", "success", 0);
							status = ClientStatus.IN_LOBBY;
							username = msg.getText();
							System.out.println("User \""+username+"\" logged in.");
						} else if (msg.getAction().equals("logout")) {
							reply = new Message(MessageType.LOGIN, "logout", "success", 0);
							status = ClientStatus.NOT_LOGGED_IN;
							exit = true;
							System.out.println("User \""+username+"\" logged out. Disconnecting client...");
						}
						break;
					case BALANCE:
						break;
					case GAME:
						break;
					case LOBBY:
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
