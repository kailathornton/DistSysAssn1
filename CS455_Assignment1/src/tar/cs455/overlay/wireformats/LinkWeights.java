package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cs455.overlay.node.Node;

public class LinkWeights implements Event {
	private int MessageType;
	private String senderNodeID;
	private int numberofLinks;
	public String linkInfo1;
	public String linkInfo2;
	
	public LinkWeights(Node node){
		MessageType = Protocol.LINK_WEIGHTS;
		senderNodeID = node.getNodeID();
		numberofLinks = 2;
		linkInfo1 = null;
		linkInfo2 = null;
	}
	public LinkWeights(byte[] data) throws IOException{
		//receiving message

				ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
				DataInputStream din = new DataInputStream (new BufferedInputStream(baInputStream));

				MessageType = din.readInt();
				
				numberofLinks = din.readInt();
				
				int c1Length = din.readInt();
				byte[] c1bytes = new byte[c1Length];
				din.readFully(c1bytes);
				linkInfo1 = new String(c1bytes);
				
				int c2Length = din.readInt();
				byte[] c2bytes = new byte[c2Length];
				din.readFully(c2bytes);
				linkInfo2 = new String(c2bytes);
				
				int IDLength = din.readInt();
				byte[] IDBytes = new byte[IDLength];
				din.readFully(IDBytes);
				
				senderNodeID = new String(IDBytes);
				
				
				baInputStream.close();
				
				din.close();
		
		
	}
	
	public void assignWeight(String info1, String info2){
		
		linkInfo1 = info1;
		linkInfo2 = info2;
		
		
	}
	
	public int getType() {
		return MessageType;
	}

	public byte[] getBytes() throws IOException {
		byte[] marshalledBytes = null;

		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

		dout.writeInt(MessageType);

		dout.writeInt(numberofLinks);

		byte[] c1 = linkInfo1.getBytes();
		int c1length = c1.length;

		dout.writeInt(c1length);
		dout.write(c1);

		byte[] c2 = linkInfo2.getBytes();
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

}
