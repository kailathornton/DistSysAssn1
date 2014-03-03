package cs455.overlay.wireformats;

import cs455.overlay.node.Node;

public class TaskInitiate implements Event{
	private int MessageType;
	private String senderNodeID;
	
	public TaskInitiate(Node node){
		MessageType = Protocol.TASK_INITIATE;
		senderNodeID = node.getNodeID();;
	}
	
	public TaskInitiate(byte[] data){
		
	}

	public int getType() {
		return MessageType;
	}

	@Override
	public byte[] getBytes() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSenderName() {
		return senderNodeID;
	}

}
