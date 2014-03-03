package cs455.overlay.node;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import cs455.overlay.transport.TCPSender;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.wireformats.Deregister;
import cs455.overlay.wireformats.DeregisterResponse;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.EventFactory;
import cs455.overlay.wireformats.LinkWeights;
import cs455.overlay.wireformats.MessagingNodesList;
import cs455.overlay.wireformats.Protocol;
import cs455.overlay.wireformats.Register;
import cs455.overlay.wireformats.RegisterResponse;

public class Registry implements Node{
	private int portNum;
	private ServerSocket serverSock;
	private TCPSender sender;
	private TCPServerThread server;
	private String nodeID;
	EventFactory eventsF;
	private int msgNodeCtr;

	private HashMap<Integer, Connection> connections;
	private ArrayList<String> links;


	public Registry(int portnum){
		portNum = portnum;
		server = null;
		sender = null;
		connections = new HashMap<Integer,Connection>();
		eventsF = EventFactory.getInstance();
		links = new ArrayList<String>();

	}



	public void run(){
		connect();

	}

	private void connect(){
		try {
			server = new TCPServerThread(portNum, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		server.start();

	}

	synchronized public void onEvent(Event e) {
		int messageType = e.getType();
		Connection c = null;


		switch(messageType){
		case Protocol.REGISTER_REQUEST:
			Register r = (Register)e;

			try {
				c = new Connection(this, new Socket(r.IPAddress,r.PortNumber));
			} catch (UnknownHostException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

			boolean registered = registerConnection(c);

			//needs to send a registration response now!
			RegisterResponse response = null;
			try {
				response = (RegisterResponse) (eventsF.createBlankEvent(Protocol.REGISTER_RESPONSE, this));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			if(registered){
				msgNodeCtr++;
				response.setStatus((byte) 1);
				response.setInfo("Registration request successful.  Number of messaging nodes currently in overlay is: " + connections.size());
			}
			else {
				response.setStatus((byte) 0);
				response.setInfo("Registration request unsuccessful! ");
			}


			try {
				c.sender.sendData(response.getBytes());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			break;




		case Protocol.REGISTER_RESPONSE:
			System.err.println("ERROR: Registry should not receive registration responses! ");
			break;
			
			
		case Protocol.DEREGISTER_REQUEST:
			Deregister d = (Deregister)e;


			c = connections.get(d.PortNumber);

			DeregisterResponse deResponse = null;

			try {
				deResponse = (DeregisterResponse)(eventsF.createBlankEvent(Protocol.DEREGISTER_RESPONSE, this));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if(c == (null)){
				deResponse.setStatus((byte) 0);
				deResponse.setInfo("Deregistration unsuccessful! Messaging node never registered with Registry ");
				try {
					Connection nullC = new Connection(this, new Socket(d.IPAddress,d.PortNumber));
					nullC.sender.sendData(deResponse.getBytes());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
				msgNodeCtr--;
				deResponse.setStatus((byte) 1);
				deResponse.setInfo("Deregistration request successful!");
				deregisterConnection(c);
				try{
					c.sender.sendData(deResponse.getBytes());
				}catch(IOException e1){
					e1.printStackTrace();
				}
			}

			break;




		case Protocol.DEREGISTER_RESPONSE:
			System.err.println("ERROR: Registry should not receive deregistration responses!");
		case Protocol.LINK_WEIGHTS:

		case Protocol.MESSAGING_NODES_LIST:

		case Protocol.PULL_TRAFFIC_SUMMARY:

		case Protocol.TASK_COMPLETE:

		case Protocol.TASK_INITIATE:
		case Protocol.TRAFFIC_SUMMARY:
		case Protocol.ROUNDS_MESSAGE:
		case Protocol.CONNECT_BACK:

		default:
		}

	}



	synchronized public boolean registerConnection(Connection c) {
		if(connections.containsKey(c.key)) return false;
		else{	
			connections.put(c.key, c);
			return true;
		}
	}

	synchronized public boolean deregisterConnection(Connection c) {
		if(connections.containsKey(c.key)){		
			connections.remove(c.key);
			return true;
		}
		else return false;
	}




	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String ID) {
		nodeID = ID;

	}

	public void setupOverlay(){
		//get a list of all the connections currently in the map

		ArrayList<Connection> ALconnections = new ArrayList<Connection>();
		for(Map.Entry<Integer, Connection> entry : connections.entrySet()){
			
			ALconnections.add(entry.getValue());
		}
		

		Connection c0 = ALconnections.get(0);
		Connection c1 = ALconnections.get(1);
		Connection c2 = ALconnections.get(2);
		Connection c3 = ALconnections.get(3);
		Connection c4 = ALconnections.get(4);
		Connection c5 = ALconnections.get(5);
		Connection c6 = ALconnections.get(6);
		Connection c7 = ALconnections.get(7);
		Connection c8 = ALconnections.get(8);
		Connection c9 = ALconnections.get(9);


		//tell each overlay to connect to each other..

		ArrayList<Connection> node0 = new ArrayList<Connection>();
		ArrayList<Connection> node1 = new ArrayList<Connection>();
		ArrayList<Connection> node2 = new ArrayList<Connection>();
		ArrayList<Connection> node3 = new ArrayList<Connection>();
		ArrayList<Connection> node4 = new ArrayList<Connection>();
		ArrayList<Connection> node5 = new ArrayList<Connection>();
		ArrayList<Connection> node6 = new ArrayList<Connection>();
		ArrayList<Connection> node7 = new ArrayList<Connection>();
		ArrayList<Connection> node8 = new ArrayList<Connection>();
		ArrayList<Connection> node9 = new ArrayList<Connection>();

		node0.add(c1);
		node0.add(c2);
		Object[] n0 = {c0, node0};
		sendOverlay(n0);

		node1.add(c2);
		node1.add(c3);
		Object[] n1 = {c1, node1};
		sendOverlay(n1);


		node2.add(c3);
		node2.add(c4);
		Object[] n2 = {c2, node2};
		sendOverlay(n2);


		node3.add(c4);
		node3.add(c5);
		Object[] n3 = {c3, node3};
		sendOverlay(n3);


		node4.add(c5);
		node4.add(c6);
		Object[] n4 = {c4, node4};
		sendOverlay(n4);


		node5.add(c6);
		node5.add(c7);
		Object[] n5 = {c5, node5};
		sendOverlay(n5);


		node6.add(c7);
		node6.add(c8);
		Object[] n6 = {c6, node6};
		sendOverlay(n6);


		node7.add(c8);
		node7.add(c9);
		Object[] n7 = {c7, node7};
		sendOverlay(n7);


		node8.add(c9);
		node8.add(c0);
		Object[] n8 = {c8, node8};
		sendOverlay(n8);


		node9.add(c0);
		node9.add(c1);
		Object[] n9 = {c9, node9};
		sendOverlay(n9);


	}


	public void sendOverlay(Object[] info){
		
		Connection connectingNode = (Connection) info[0];
		

		@SuppressWarnings("unchecked")
		ArrayList<Connection> connectingList = (ArrayList<Connection>) info[1];
		try {
			MessagingNodesList msgList = (MessagingNodesList)(eventsF.createBlankEvent(Protocol.MESSAGING_NODES_LIST, this));
			msgList.setConnections(connectingList);

			connectingNode.sender.sendData(msgList.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	
	
	public void assignLinks(){
		 Random gen = new Random();
		 
 
		 
		//get a list of all the connections currently in the map

			ArrayList<Connection> ALconnections = new ArrayList<Connection>();
			for(Map.Entry<Integer, Connection> entry : connections.entrySet()){
				
				ALconnections.add(entry.getValue());
			}
			

			Connection c0 = ALconnections.get(0);
			Connection c1 = ALconnections.get(1);
			Connection c2 = ALconnections.get(2);
			Connection c3 = ALconnections.get(3);
			Connection c4 = ALconnections.get(4);
			Connection c5 = ALconnections.get(5);
			Connection c6 = ALconnections.get(6);
			Connection c7 = ALconnections.get(7);
			Connection c8 = ALconnections.get(8);
			Connection c9 = ALconnections.get(9);


					
			String temp = "";
			String temp2 = "";
			

			temp = c1.socket.getInetAddress().getCanonicalHostName() + ":" + c1.socket.getPort() + " "+gen.nextInt(11);
			temp2 = c2.socket.getInetAddress().getCanonicalHostName() + ":" + c2.socket.getPort() +" "+gen.nextInt(11);

			Object[] n0 = {c0, temp, temp2};
			sendLinks(n0);

			
			temp = c2.socket.getInetAddress().getCanonicalHostName() + ":" + c2.socket.getPort() +" "+gen.nextInt(11);
			temp2 = c3.socket.getInetAddress().getCanonicalHostName() + ":" + c3.socket.getPort() +" "+gen.nextInt(11);

			Object[] n1 = {c1, temp, temp2};
			sendLinks(n1);


			temp = c3.socket.getInetAddress().getCanonicalHostName() + ":" + c3.socket.getPort() +" "+gen.nextInt(11);
			temp2 = c4.socket.getInetAddress().getCanonicalHostName() + ":" + c4.socket.getPort() +" "+gen.nextInt(11);

			Object[] n2 = {c2, temp, temp2};
			sendLinks(n2);


			temp = c4.socket.getInetAddress().getCanonicalHostName() + ":" + c4.socket.getPort() +" "+gen.nextInt(11);
			temp2 = c5.socket.getInetAddress().getCanonicalHostName() + ":" + c5.socket.getPort() +" "+gen.nextInt(11);

			Object[] n3 = {c3, temp, temp2};
			sendLinks(n3);


			temp = c5.socket.getInetAddress().getCanonicalHostName() + ":" + c5.socket.getPort() +" "+gen.nextInt(11);
			temp2 = c6.socket.getInetAddress().getCanonicalHostName() + ":" + c6.socket.getPort() +" "+gen.nextInt(11);

			Object[] n4 = {c4, temp, temp2};
			sendLinks(n4);


			temp = c6.socket.getInetAddress().getCanonicalHostName() + ":" + c6.socket.getPort() +" "+gen.nextInt(11);
			temp2 = c7.socket.getInetAddress().getCanonicalHostName() + ":" + c7.socket.getPort() +" "+gen.nextInt(11);

			Object[] n5 = {c5, temp, temp2};
			sendLinks(n5);


			temp = c7.socket.getInetAddress().getCanonicalHostName() + ":" + c7.socket.getPort() +" "+gen.nextInt(11);
			temp2 = c8.socket.getInetAddress().getCanonicalHostName() + ":" + c8.socket.getPort() +" "+gen.nextInt(11);

			Object[] n6 = {c6, temp, temp2};
			sendLinks(n6);


			temp = c8.socket.getInetAddress().getCanonicalHostName() + ":" + c8.socket.getPort() +" "+gen.nextInt(11);
			temp2 = c9.socket.getInetAddress().getCanonicalHostName() + ":" + c9.socket.getPort() +" "+gen.nextInt(11);

			Object[] n7 = {c7, temp, temp2};
			sendLinks(n7);


			temp = c9.socket.getInetAddress().getCanonicalHostName() + ":" + c9.socket.getPort() +" "+gen.nextInt(11);
			temp2 = c0.socket.getInetAddress().getCanonicalHostName() + ":" + c0.socket.getPort() +" "+gen.nextInt(11);

			Object[] n8 = {c8, temp, temp2};
			sendLinks(n8);


			temp = c0.socket.getInetAddress().getCanonicalHostName() + ":" + c0.socket.getPort() +" "+gen.nextInt(11);
			temp2 = c1.socket.getInetAddress().getCanonicalHostName() + ":" + c1.socket.getPort() +" "+gen.nextInt(11);

			Object[] n9 = {c9, temp, temp2};
			sendLinks(n9);


		
		
	}
	
	public void sendLinks(Object[] info){
		
		Connection connectingNode = (Connection) info[0];
		
		try {
			LinkWeights lw = (LinkWeights)(eventsF.createBlankEvent(Protocol.LINK_WEIGHTS, this));
			lw.assignWeight((String)info[1], (String)info[2]);
			
			
			
			connectingNode.sender.sendData(lw.getBytes());
			
			
			
			String temp = connectingNode.socket.getInetAddress().getCanonicalHostName() + ":" + connectingNode.socket.getPort() +
					" " + info[1];
			
			links.add(temp);
			temp = connectingNode.socket.getInetAddress().getCanonicalHostName() + ":" + connectingNode.socket.getPort() +
					" " + info[2];
			links.add(temp);
						
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void printWeights(){
		for(int i = 0; i < links.size(); i++){
			System.out.println(links.get(i));
		}
		
	}
	
	
	
	public void getInfo(){
		
		ArrayList<Connection> ALconnections = new ArrayList<Connection>();
		for(Map.Entry<Integer, Connection> entry : connections.entrySet()){
			
			ALconnections.add(entry.getValue());
		}
		
		for(int i = 0; i < ALconnections.size(); i++){
					
			String temp = ALconnections.get(i).socket.getInetAddress().getCanonicalHostName() + ":" + ALconnections.get(i).socket.getPort();
			
			System.out.println(temp);
			
			
		}
		
		
		
		
	}
	
	synchronized public int getnumConnections(){
		return connections.size();
	}

	public static void main(String[] args){
		Registry registry = new Registry(Integer.parseInt(args[0]));
		registry.run();


		Scanner keyboard = new Scanner(System.in);

		while(true){
			String line = keyboard.nextLine();

			switch(line){
			case "list-messaging nodes":
				registry.getInfo();
				break;
			case "list-weights":
				registry.printWeights();
				break;
			case "setup-overlay 4":
				if(registry.getnumConnections() < 4)
					System.err.println("ERROR: Number of connections is less than overlay size!");				
				registry.setupOverlay();
				break;
			case "send-overlay-link-weights":
				registry.assignLinks();
				break;
			case "start":
				System.out.println("OH BOY!!");
				break;
			default:
				System.err.println("ERROR: Invalid Command!");
			}
		}

	}

}
