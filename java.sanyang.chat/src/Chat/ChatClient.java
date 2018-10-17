package Chat;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * arr[0] is host
 * arr[1] is port
 * if the server is local then the host is localhost otherwise IP-address
 * the port is 5003
 * @author Palif
 */

public class ChatClient {
	
	private static InetAddress remoteAddr;
	private static Integer remotePort;
	private static Socket socket = null;
	private static Scanner in;
	
	private static PrintWriter sout;
	private static BufferedReader sin;
	
	public static void main(String[] arr) {
		
		try {
			
			String input;
			boolean online = true;
			
			in = new Scanner(System.in);
			
			remoteAddr = InetAddress.getByName(arr[0]);
			remotePort = Integer.parseInt(arr[1]);
			socket = new Socket(remoteAddr, remotePort);
			
			sout = new PrintWriter(socket.getOutputStream());
			sin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			ReadMessageThread rmt = new ReadMessageThread(sin);
			rmt.start();
			
			while (online) {
				input = in.nextLine();
				if(!rmt.isAlive()) {
					online = false;
					continue;
				}
				sout.println(input);
				sout.flush();
			}
			
			System.out.println("logout..");
		}
		catch (UnknownHostException e) {
			System.out.println("Exception unknown host: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Exception io: " + e.getMessage());
		}
		catch (NullPointerException e) {
			System.out.println("Exception null pointer: " + e.getMessage());
		}
		finally {
			try {
				if (socket != null) socket.close();
				if (sin != null) sin.close();
				if (sout != null) sout.close();
				if (in != null) in.close();
			} catch (IOException e) {
				e.getMessage();
			}
		}
		
		System.out.println("...exited");
		System.exit(0);
		
	}

}

class ReadMessageThread extends Thread {
	
	private BufferedReader sin = null;
	private String input = null;
	private boolean online;
	
	ReadMessageThread(BufferedReader sin){
		this.sin = sin;
		this.online = true;
	}
	
	@Override
	public void run() {
		
		try {
			while((this.input = this.sin.readLine()) != null && this.online) {	
				if (this.input.equals("/exit")) {
					System.out.println("Initiating thread teardown..");
					this.online = false;
					continue;
				}
				System.out.println(this.input);
			}	
			System.out.println("ReadMessageThread thread teardown completed.");
			System.out.println("Press ENTER to exit..");
		}
		catch(IOException e) {
			System.out.println("IO exception in ChatClientThread: " + e.getMessage());
		}
		finally {
				try {
					if(this.sin != null) this.sin.close();
				} catch (Exception e) {
					e.getMessage();
				}
		}
		
	}
	
}
