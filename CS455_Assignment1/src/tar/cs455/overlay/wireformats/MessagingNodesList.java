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

public class MessagingNodesList implements Event{

	private int MessageType;
	private int numPeer;
	public String connect1;
	public String connect2;
	private String senderNodeID;



	public MessagingNodesList(Node node){
		senderNodeID = node.getNodeID();
		MessageType = Protocol.MESSAGING_NODES_LIST;
		numPeer = 2;
		connect1 = null;
		connect2 = null;
	}

	public MessagingNodesList(byte[] data) throws IOException{
		//receiving message

		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream (new BufferedInputStream(baInputStream));

		MessageType = din.readInt();
		
		numPeer = din.readInt();
		
		int c1Length = din.readInt();
		byte[] c1bytes = new byte[c1Length];
		din.readFully(c1bytes);
		connect1 = new String(c1bytes);
		
		int c2Length = din.readInt();
		byte[] c2bytes = new byte[c2Length];
		din.readFully(c2bytes);
		connect2 = new String(c2bytes);
		
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

		byte[] c1 = connect1.getBytes();
		int c1length = c1.length;

		dout.writeInt(c1length);
		dout.write(c1);

		byte[] c2 = connect2.getBytes();
		int c2length = c2.length;

		dout.writeInt(c2length);
		dout.write(c2);

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

	public void setConnections(ArrayList<Connection> c){
		connect1 = c.get(0).socket.getInetAddress().getCanonicalHostName() + ":" + c.get(0).socket.getPort();
		connect2 = c.get(1).socket.getInetAddress().getCanonicalHostName() + ":" + c.get(1).socket.getPort();
	}

	//message the registry sends out to notify who needs to connect to who




}
