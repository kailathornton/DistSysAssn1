package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cs455.overlay.node.Node;

public class DeregisterResponse implements Event{
	private int MessageType;
	public int StatusCode;
	private String AdditionalInfo;	
	private String senderNodeID;
	
	public DeregisterResponse(Node node){
		MessageType = Protocol.DEREGISTER_RESPONSE;
		StatusCode = 0;
		AdditionalInfo = "";
		senderNodeID = node.getNodeID();
	}
	
	public DeregisterResponse(byte[] data) throws IOException{
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream din = new DataInputStream (new BufferedInputStream(baInputStream));
		
		MessageType = din.readInt();
		
		StatusCode = din.readInt();
		
		int infoLength = din.readInt();
		byte[] infoBytes = new byte[infoLength];
		din.readFully(infoBytes);
		
		AdditionalInfo = new String(infoBytes);
		
		int senderIDLength = din.readInt();
		byte[] idBytes = new byte[senderIDLength];
		din.readFully(idBytes);
		senderNodeID = new String(idBytes);
		
			
		baInputStream.close();
		
		din.close();
		
		
	}
	
	
	public int getType() {
		return MessageType;
	}

	@Override
	public byte[] getBytes() throws IOException {
		byte[] marshalledBytes = null;

		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

		dout.writeInt(MessageType);
		

		dout.writeInt(StatusCode);

		byte[] AdditionalInfoBytes = AdditionalInfo.getBytes();
		int AILength = AdditionalInfoBytes.length;

		dout.writeInt(AILength);
		dout.write(AdditionalInfoBytes);
		
		byte[] senderIDBytes = senderNodeID.getBytes();
		int IDLength = senderIDBytes.length;
		
		dout.writeInt(IDLength);
		dout.write(senderIDBytes);		

		dout.flush();

		marshalledBytes = baOutputStream.toByteArray();

		baOutputStream.close();
		dout.close();

		return marshalledBytes;
	}

	public String getSenderName() {
		return senderNodeID;
	}
	
	public void setStatus(byte status){
		StatusCode = status;
	}
	public void setInfo(String info){
		AdditionalInfo = info;
	}
}
