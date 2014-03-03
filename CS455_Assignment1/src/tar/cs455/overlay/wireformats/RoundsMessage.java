package cs455.overlay.wireformats;

import java.io.IOException;

import cs455.overlay.node.Node;

public class RoundsMessage implements Event {

	public RoundsMessage(Node node){
		
	}
	
	public RoundsMessage(byte[] data){
		
	}
	
	
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] getBytes() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSenderName() {
		// TODO Auto-generated method stub
		return null;
	}

}
