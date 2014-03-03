package cs455.overlay.wireformats;

public class Protocol {

	public static final int REGISTER_REQUEST = 1;
	public static final int REGISTER_RESPONSE = 2;
	public static final int DEREGISTER_REQUEST = 3;
	public static final int DEREGISTER_RESPONSE = 4;
	public static final int MESSAGING_NODES_LIST = 5;
	public static final int LINK_WEIGHTS = 6;
	public static final int TASK_INITIATE = 7;
	public static final int TASK_COMPLETE = 8;
	public static final int PULL_TRAFFIC_SUMMARY = 9;
	public static final int TRAFFIC_SUMMARY = 10;
	public static final int ROUNDS_MESSAGE = 11;
	public static final int CONNECT_BACK = 12;
	
	public String getMessageType(int msg){
		String type = "";
		
		
		switch(msg){
		case REGISTER_REQUEST:
			type = "REGISTER_REQUEST";
			break;
		case REGISTER_RESPONSE:
			type = "REGISTER_RESPONSE";
			break;

		case DEREGISTER_REQUEST:
			type = "DEREGISTER_REQUEST";
			break;

		case DEREGISTER_RESPONSE:
			type = "DEREGISTER_RESPONSE";
			break;

		case MESSAGING_NODES_LIST:
			type = "MESSAGING_NODES_LIST";
			break;

		case LINK_WEIGHTS:
			type = "LINK_WEIGHTS";
			break;

		case TASK_INITIATE:
			type = "TASK_INITIATE";
			break;

		case TASK_COMPLETE:
			type = "TASK_COMPLETE";
			break;

		case PULL_TRAFFIC_SUMMARY:
			type = "PULL_TRAFFIC_SUMMARY";
			break;
		case TRAFFIC_SUMMARY:
			type = "TRAFFIC_SUMMARY";
			break;
		case ROUNDS_MESSAGE:
			type = "ROUNDS_MESSAGE";
			break;
			
		case CONNECT_BACK:
			type = "CONNECT_BACK";
			break;

		
		}

		return type;
	}
	
	
	
	
	
	
}
