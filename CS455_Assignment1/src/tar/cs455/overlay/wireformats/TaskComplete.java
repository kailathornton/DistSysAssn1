package cs455.overlay.wireformats;

import cs455.overlay.node.Node;

public class TaskComplete implements Event{
	private int MessageType;
	private String senderNodeID;

	public TaskComplete(Node node){
		MessageType = Protocol.TASK_COMPLETE;
		senderNodeID = node.getNodeID();
	}
	public TaskComplete(byte[] data){
	
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
