package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import cs455.overlay.node.Connection;
import cs455.overlay.node.Node;

//Singleton isntance, factory that actually returns created messages



public class EventFactory {

	private static EventFactory instance = null;

	protected EventFactory(){

	}

	public static EventFactory getInstance(){

		if(instance == null){
			instance = new EventFactory();
		}

		return instance;



	}public Event createBlankEvent(int type, Node node) throws IOException{

		switch(type){
		case Protocol.REGISTER_REQUEST:
			return new Register(node);


		case Protocol.REGISTER_RESPONSE:
			return new RegisterResponse(node);

		case Protocol.DEREGISTER_REQUEST:
			return new Deregister(node);

		case Protocol.DEREGISTER_RESPONSE:
			return new DeregisterResponse(node);
		case Protocol.LINK_WEIGHTS:
			return new LinkWeights(node);

		case Protocol.MESSAGING_NODES_LIST:
			return new MessagingNodesList(node);

		case Protocol.PULL_TRAFFIC_SUMMARY:
			return new TaskSummaryRequest(node);

		case Protocol.TASK_COMPLETE:
			return new TaskComplete(node);

		case Protocol.TASK_INITIATE:
			return new TaskInitiate(node);
		case Protocol.TRAFFIC_SUMMARY:
			return new TaskSummaryResponse(node);
		case Protocol.ROUNDS_MESSAGE:
			return new RoundsMessage(node);
		case Protocol.CONNECT_BACK:
			return new ConnectBack(node);

		default:
			return null;
		}


	}


	public Event createEventBytes(byte[] data) throws IOException{
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(data);
		DataInputStream byteInputStream = new DataInputStream(new BufferedInputStream(baInputStream));

		int type = byteInputStream.readInt();

		switch(type){
		case Protocol.REGISTER_REQUEST:
			try {
				return new Register(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		case Protocol.REGISTER_RESPONSE:

			try {
				return new RegisterResponse(data);
			} catch (IOException e) {
				e.printStackTrace();
			}


		case Protocol.DEREGISTER_REQUEST:
			return new Deregister(data);

		case Protocol.DEREGISTER_RESPONSE:
			return new DeregisterResponse(data);
		case Protocol.LINK_WEIGHTS:
			return new LinkWeights(data);

		case Protocol.MESSAGING_NODES_LIST:
			return new MessagingNodesList(data);

		case Protocol.PULL_TRAFFIC_SUMMARY:
			return new TaskSummaryRequest(data);

		case Protocol.TASK_COMPLETE:
			return new TaskComplete(data);

		case Protocol.TASK_INITIATE:
			return new TaskInitiate(data);
		case Protocol.TRAFFIC_SUMMARY:
			return new TaskSummaryResponse(data);
			
		case Protocol.ROUNDS_MESSAGE:
			return new RoundsMessage(data);
		case Protocol.CONNECT_BACK:
			return new ConnectBack(data);
		default:
			return null;
		}


	}






}
