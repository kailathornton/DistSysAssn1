package cs455.overlay.transport;

import java.io.IOException;
import java.net.Socket;

import cs455.overlay.node.Node;


public class Connection {


	Socket socket;
	String name;
	Node node;
	TCPReceiverThread receiver;
	TCPSender sender;
	
	
	
	Connection(Node node, Socket socket) {
		this.socket = socket;
		this.name = getName();
		this.node = node;
		try {
			this.receiver = new TCPReceiverThread(socket, node);
			sender = new TCPSender(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		node.registerConnection(this);
	}
	
	
	public String getName(){
		return socket.getLocalAddress().toString() + ":" + socket.getPort();
	}
	
	public boolean sendData(byte[] bytes){
		return false;
	}
	
	
	
	
}
