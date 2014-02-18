package cs455.overlay.transport;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

import cs455.overlay.node.Node;

public class TCPReceiverThread extends Thread{

	private Socket socket;	
	private DataInputStream din;
	private Node node;


	public TCPReceiverThread(Socket socket, Node node) throws IOException {	
		this.socket = socket;	
		din = new DataInputStream(socket.getInputStream());	
		this.node = node; 
	}	

	public void run() {	
		System.out.println("Receiver thread is running");
		
		
		int dataLength;	
		while (socket != null) {	
			try {	
				dataLength = din.readInt(); 		

				byte[] data = new byte[dataLength];	
				din.readFully(data, 0, dataLength);	
				
				System.out.println("This is the byte array in the TCP Receiver");
				System.out.println(Arrays.toString(data));
				//node.onEvent();

			} catch (SocketException se) {	
				System.out.println(se.getMessage());	
				break;	
			} catch (IOException ioe) {	
				System.out.println(ioe.getMessage()) ;	
				break;	
			}	
		}	
		
		
		
		
		
		
	} 	



}
