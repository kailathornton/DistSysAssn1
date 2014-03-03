package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cs455.overlay.node.Node;

public class Register implements Event{

	private int MessageType;
	public String IPAddress;
	public int PortNumber;
	private String senderNodeID;
	
	
	public Register(Node node){
		MessageType = Protocol.REGISTER_REQUEST;
		senderNodeID = node.getNodeID();
		parseID();
	}
	
	public Register(byte[] bArray)throws IOException{
		//receiving message
	
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(bArray);
		DataInputStream din = new DataInputStream (new BufferedInputStream(baInputStream));
		
		MessageType = din.readInt();
		
		int IPlength = din.readInt();
		byte[] ipBytes = new byte[IPlength];
		din.readFully(ipBytes);
		
		IPAddress = new String(ipBytes);
		
		PortNumber = din.readInt();
		
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
		//sending message
		
		byte[] marshalledBytes = null;
		
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
		
		dout.writeInt(MessageType);
		
		byte[] ipBytes = IPAddress.getBytes();
		int ipLength = ipBytes.length;
		
		dout.writeInt(ipLength);
		dout.write(ipBytes);
		
		dout.writeInt(PortNumber);
		
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
	
	
	public void parseID(){
		
		
		String id = senderNodeID;
		
		String IP = "";
		String port = "";
		
		
		boolean flag = false;
		
		for(int i = 0; i < id.length(); i++){
			if(id.charAt(i) == ':'){
				flag = true;
				continue;
			}
			
			if(!flag){
				IP += id.charAt(i);
			}else{
				port += id.charAt(i);
			}
		
		}
		
		IPAddress = IP;
		PortNumber = Integer.parseInt(port);
		

		
	}


	public String getSenderName() {
		return senderNodeID;
	}
	
	
	
	
	
	
	

}
