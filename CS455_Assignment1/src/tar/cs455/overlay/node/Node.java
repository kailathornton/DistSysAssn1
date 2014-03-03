package cs455.overlay.node;

import java.util.ArrayList;

import cs455.overlay.wireformats.Event;

public interface Node {	
		
	void onEvent(Event e);
	
	boolean registerConnection(Connection c);
	
	boolean deregisterConnection(Connection c);
	
	String getNodeID();
	
	void setNodeID(String ID);
	
	
	
	
}
