package cs455.overlay.node;

import java.io.IOException;
import java.net.Socket;

import cs455.overlay.transport.TCPReceiverThread;
import cs455.overlay.transport.TCPSender;


public class Connection {

	public final Socket socket;
	Node node;
	TCPReceiverThread receiver;
	TCPSender sender;
	public final int key;

	Connection(Node node, Socket socket) {
		this.socket = socket;
		this.node = node;
		key = socket.getPort();
			
		try {
			this.receiver = new TCPReceiverThread(socket, node);
			sender = new TCPSender(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public boolean sendData(byte[] bytes){
		
		try {
			sender.sendData(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	
	public String toString(){
		
		return key+"";
	}
	
	
	
}
