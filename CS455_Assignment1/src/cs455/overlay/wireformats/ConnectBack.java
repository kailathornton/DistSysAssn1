package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import cs455.overlay.node.Connection;
import cs455.overlay.node.Node;

public class ConnectBack implements Event {
	
	private int MessageType;
	private int numPeer;
	public String connect;
	private String senderNodeID;
	
	public ConnectBack(Node node){
		senderNodeID = node.getNodeID();
		MessageType = Protocol.CONNECT_BACK;
		numPeer = 1;
		connect = null;
			
	}
	
	public ConnectBack(byte[] data) throws IOException{
		
		//receiving message

				ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
				DataInputStream din = new DataInputStream (new BufferedInputStream(baInputStream));

				MessageType = din.readInt();
				
				numPeer = din.readInt();
				
				int c1Length = din.readInt();
				byte[] c1bytes = new byte[c1Length];
				din.readFully(c1bytes);
				connect = new String(c1bytes);
				
				int IDLength = din.readInt();
				byte[] IDBytes = new byte[IDLength];
				din.readFully(IDBytes);
				
				senderNodeID = new String(IDBytes);
				
				
				baInputStream.close();
				
				din.close();
			
				
				
	}
	
	public int getType() {
		return MessageType;
	}

	public byte[] getBytes() throws IOException {
		

		//		private int MessageType;
		//		private int numPeer;
		//		private String connect1;
		//		private String connect2;
		//		private String senderNodeID;

		byte[] marshalledBytes = null;

		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

		dout.writeInt(MessageType);

		dout.writeInt(numPeer);

		byte[] c1 = connect.getBytes();
		int c1length = c1.length;

		dout.writeInt(c1length);
		dout.write(c1);

		byte[] IDBytes = senderNodeID.getBytes();
		int IDLength = IDBytes.length;

		dout.writeInt(IDLength);
		dout.write(IDBytes);

		dout.flush();

		marshalledBytes = baOutputStream.toByteArray();

		baOutputStream.close();
		dout.close();


		return marshalledBytes;



	}

	public String getSenderName() {
		return senderNodeID;
	}

	public void setConnection(Connection c){
		connect = c.socket.getInetAddress().getCanonicalHostName() + ":" + c.socket.getPort();
	}


}
