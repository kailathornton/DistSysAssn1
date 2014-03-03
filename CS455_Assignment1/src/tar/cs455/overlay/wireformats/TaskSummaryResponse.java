package cs455.overlay.wireformats;

import cs455.overlay.node.Node;

public class TaskSummaryResponse implements Event{
	private String senderNodeID;
	private int MessageType;

	public TaskSummaryResponse(Node node){
		MessageType = Protocol.TRAFFIC_SUMMARY;
		senderNodeID = node.getNodeID();
	}
	
	public TaskSummaryResponse(byte[] data){
		
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
