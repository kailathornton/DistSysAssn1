package cs455.overlay.node;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import cs455.overlay.transport.Connection;
import cs455.overlay.transport.TCPSender;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.wireformats.Event;

public class MessagingNode implements Node {
	
	private SocketAddress registryInfo;

	public MessagingNode() {
	//	registryInfo = info;
	}

	public void run() {
		
		connection();
	}
	
	public void connection(){
		try {
			
			TCPServerThread serverThread = new TCPServerThread(0, this);
			serverThread.start();
			
			
			Socket socket = new Socket();
//			socket.connect(registryInfo);
			
		//	TCPSender sender = new TCPSender(socket);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	synchronized public void onEvent(Event e) {
		// TODO Auto-generated method stub

	}

	@Override
	synchronized public void registerConnection(Connection c) {
		// TODO Auto-generated method stub

	}

	@Override
	synchronized public void deregisterConnection(Connection c) {
		// TODO Auto-generated method stub

	}

}
