package cs455.overlay.transport;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import cs455.overlay.node.Node;

public class TCPServerThread extends Thread {

	private ServerSocket socket;
	private Node node;

	public TCPServerThread(int portNum, Node node) throws IOException {
		this.socket = new ServerSocket(portNum);
		this.node = node;
		setNodeID();

	}

	public void run() {

		try {

			while (true) {
				Socket receivedSocket = socket.accept();

					TCPReceiverThread receiverThread = new TCPReceiverThread(receivedSocket, node);
					
					receiverThread.start();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		disconnect();
	}

	private void disconnect(){
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void setNodeID(){
		String ID = "";
	
		
		try {
			 ID = InetAddress.getLocalHost().getCanonicalHostName() + ":" +  socket.getLocalPort();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		node.setNodeID(ID);

		

		
		
	}
	
	
}
