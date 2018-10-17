package Chat;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ChatServer {
	
	private static ArrayList<PrintWriter> clientOutStreamList;
	private static ArrayList<String> clientInfoList;
	private static Socket clientSock;
	private static ServerSocket server;
	
	public static void main(String[] arr) throws IOException {
		
		boolean listening = true;
		int port = Integer.parseInt(arr[0]);
		
		try {
			clientOutStreamList = new ArrayList<>();
			clientInfoList = new ArrayList<>();
			
			server = new ServerSocket(port);
			
			while(listening) {
				clientSock = server.accept();
				System.out.println("client " + clientSock.getPort() + clientSock.getInetAddress() + " is connected" );
				ClientHandler clientHandler = new ClientHandler(clientSock, clientOutStreamList, clientInfoList);
				clientHandler.start();
			}
			
		}
		catch (IOException e) {
			System.out.println("Error in Server: " + e.getMessage());
		}
		catch (NullPointerException e) {
			System.out.println("Exception null pointer: " + e.getMessage());
		}
		finally {
			server.close();
		}
		
		
	}
	
}

class ClientHandler extends Thread {
	
	private Socket socket;
	private BufferedReader sin;
	private PrintWriter sout;
	private String stringInput;
	private String userInfo;
	private String userName;
	private ArrayList<PrintWriter> usersOutstreamList;
	private ArrayList<String> userInfoList;
	private boolean online;
	
	public ClientHandler(Socket socket, ArrayList<PrintWriter> usersOutstreamList, ArrayList<String> userInfoList) {
		this.socket = socket;
		this.usersOutstreamList = usersOutstreamList;
		this.userName = "" + this.socket.getPort() + this.socket.getInetAddress();
		this.userInfoList = userInfoList;
	}
	
	public String getMemberName() {
		return this.userName;
	}
	
	@Override
	public void run() {
		
		try {
			System.out.println("chat handler for " + this.userName + " is now active");
			System.out.println("no of member: " + this.usersOutstreamList.size());
			
			this.sin = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.sout = new PrintWriter(this.socket.getOutputStream());
			this.userInfo = this.userName + " is " + this.socket.getPort() + this.socket.getInetAddress();
			
			this.connect();
			
			/**
			 * This is where the message are sent from one client and 
			 * distributed to the rest of the members of the chat-room
			 */
			
			while ((this.stringInput = sin.readLine()) != null && online) {
				System.out.println("client " + this.socket.getPort() + ":" + this.stringInput);
				if(this.stringInput.charAt(0) == '/') {
					this.commandCall(this.stringInput);
					continue;
				}
				
				synchronized (this.usersOutstreamList) {
					for(PrintWriter out : this.usersOutstreamList) {
						if(!out.equals(this.sout)) {
							out.println(this.userName + ": " + this.stringInput);
							out.flush();
						}
					}	
				}
				System.out.println("client " + this.userName + " sent message");
			}
			
			System.out.println("ClientHandler for " + this.socket.getPort() + this.socket.getInetAddress() + " is terminated");
			this.sout.println("/exit");
			this.sout.flush();
			
		} catch (IOException e) {
			e.getMessage();
		}
		finally {
			try {
				if(this.sout != null) this.sout.close();
				if(this.sin != null) this.sin.close();
				if(this.socket != null) this.socket.close();
			} catch(IOException e) {

			}
		}
		
	}
	
	private void connect() {
		this.online = true;
		this.userInfoList.add(this.userInfo);
		this.usersOutstreamList.add(sout);
		this.sout.println("Welcome to the chat group!");
		this.sout.flush();
	}
	
	private void disconnect() {
		this.online = false;
		this.userInfoList.remove(this.userInfo);
		this.usersOutstreamList.remove(sout);
		return;
	}
	
	public void commandCall(String s) {
		//Switch could be used but I deliberately used the if statements instead
		if(s.startsWith("/nick")) {
			this.command_nick(s);
		}
		else if(s.equals("/quit")) {
			this.command_quit();
			return;
		}
		else if(s.equals("/who")) {
			this.command_who();
		}
		else if(s.equals("/help")) {
			this.command_help();
		}
		else {
			this.sout.println("Wrong command, try again");
			this.sout.flush();
		}
	}
	
	private void command_who() {
		for(String userInfo: this.userInfoList) {
			if(!userInfo.equals(this.userInfo)) {
				this.sout.println(userInfo);
				this.sout.flush();
			}
			
		}
	}
	
	private void command_help() {
		String help = 
			"/nick <nickname> - change to a nickname \n" +
				"/who - get a list of all the user in the chatgroup \n" +
					"/quit - quits the user from the chat \n" +
						"/help - get a description of available command";
		this.sout.println(help);
		this.sout.flush();
	}
	
	private void command_quit() {
		
		this.disconnect();
		
		synchronized (this.usersOutstreamList) {
			for(PrintWriter out : this.usersOutstreamList) {
				out.println(this.userName + " quit");
				out.flush();
			}	
		}
		
		this.sout.println("Press ENTER to end the clientHandler..");
		this.sout.flush();
		
		return;
		
	}	
	
	private void command_nick(String s) {
		String name = s.substring(6, s.length());
		this.userName = name;
		this.userInfoList.remove(this.userInfo);
		this.userInfo = this.userName + " is " + this.socket.getPort() + this.socket.getInetAddress();
		synchronized(this.userInfoList) {
			this.userInfoList.add(userInfo);	
		}
	}
	
}