package cs455.overlay.wireformats;

import cs455.overlay.node.Node;


public class TaskSummaryRequest implements Event{
	private int MessageType;
	private String senderNodeID;
	
	public TaskSummaryRequest(Node node){
		MessageType = Protocol.PULL_TRAFFIC_SUMMARY;
		senderNodeID = node.getNodeID();
	}
	
	public TaskSummaryRequest(byte[] data){
		
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
