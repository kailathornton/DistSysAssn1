package cs455.overlay.node;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import cs455.overlay.transport.Connection;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.wireformats.Event;

public class Registry implements Node{
	private int portNum;
	private ServerSocket serverSock;
	
	private ArrayList<Connection> connections;
	
	
//	public SocketAddress getInfo(){
//		SocketAddress info = null;
//		
//		info = serverSock.getLocalSocketAddress();
//		try {
//			System.out.println("Print this guy: " + serverSock.getInetAddress().getLocalHost().getHostAddress());
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		
//		return info;
//	}
	
	public Registry(int portnum){
		portNum = portnum;
		try {
			serverSock = new ServerSocket (portNum);
			System.out.println("** Server Socket on port:  " + portNum);
			
			
			TCPServerThread server = new TCPServerThread(portnum, this);
			server.start();
			
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		connections = new ArrayList<Connection>();
	}
	
	
	
	void run(){
		
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
