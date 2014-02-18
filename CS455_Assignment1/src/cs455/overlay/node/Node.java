package cs455.overlay.node;

import cs455.overlay.transport.Connection;
import cs455.overlay.wireformats.Event;

public interface Node {	
	
	void onEvent(Event e);
	
	void registerConnection(Connection c);
	
	void deregisterConnection(Connection c);
	
}
