package cs455.overlay.transport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cs455.overlay.node.Node;

public class TCPServerThread extends Thread {

	private ServerSocket socket;
	private Node node;

	public TCPServerThread(int portNum, Node node) throws IOException {
		this.socket = new ServerSocket(portNum);
		this.node = node;


		System.out.println("TCPServerThread listening on port: " + socket.getLocalPort());
	}

	public void run() {

		try {

			while (true) {
				Socket receivedSocket = socket.accept();

					System.out.println("TCP socket received listening on port: " + receivedSocket.getLocalPort());
					TCPReceiverThread receiverThread = new TCPReceiverThread(receivedSocket, node);
					
					receiverThread.start();

					//				new Connection(node receivedSocket);
				




			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
