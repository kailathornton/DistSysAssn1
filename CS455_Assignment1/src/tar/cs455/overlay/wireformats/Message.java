package cs455.overlay.wireformats;

import cs455.overlay.node.Node;

public class Message implements Event{
	private int MessageType;
	private String senderNodeID;
	//message you send once it gets to the 5 round thing

	public Message(Node node){
		MessageType =Protocol.ROUNDS_MESSAGE;
		senderNodeID = node.getNodeID();
	}
	public Message(byte[] data){
		
	}

	
	
	public int getType() {
		return MessageType;
	}

	@Override
	public byte[] getBytes() {
		
		
		
		
		
		
		
		return null;
	}
	public String getSenderName() {
		return senderNodeID;
	}

}
