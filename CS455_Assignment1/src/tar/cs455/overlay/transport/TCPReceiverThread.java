package cs455.overlay.transport;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

import cs455.overlay.node.Node;
import cs455.overlay.wireformats.Deregister;
import cs455.overlay.wireformats.DeregisterResponse;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.EventFactory;
import cs455.overlay.wireformats.LinkWeights;
import cs455.overlay.wireformats.MessagingNodesList;
import cs455.overlay.wireformats.Protocol;
import cs455.overlay.wireformats.Register;
import cs455.overlay.wireformats.RegisterResponse;
import cs455.overlay.wireformats.TaskComplete;
import cs455.overlay.wireformats.TaskInitiate;
import cs455.overlay.wireformats.TaskSummaryRequest;
import cs455.overlay.wireformats.TaskSummaryResponse;

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
		readIn();
	} 	

	public Socket getSocket(){
		return socket;
	}


	public void readIn(){
		int dataLength;	
		while (socket != null) {	
			try {	
				dataLength = din.readInt(); 		

				byte[] data = new byte[dataLength];	
				int type = -1;
				din.readFully(data, 0, dataLength);	
				
				node.onEvent(EventFactory.getInstance().createEventBytes(data));

			} catch (SocketException se) {	
				System.out.println(se.getMessage());	
				break;	
			} catch (IOException ioe) {	
				System.out.println(ioe.getMessage()) ;	
				break;	
			}	
		}

	}
	
	public void checkEvent(Event e){
		
		
		
	}


}
