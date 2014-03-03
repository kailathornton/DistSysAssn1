package cs455.overlay.node;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Scanner;

import cs455.overlay.transport.TCPSender;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.wireformats.ConnectBack;
import cs455.overlay.wireformats.Deregister;
import cs455.overlay.wireformats.DeregisterResponse;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.EventFactory;
import cs455.overlay.wireformats.LinkWeights;
import cs455.overlay.wireformats.MessagingNodesList;
import cs455.overlay.wireformats.Protocol;
import cs455.overlay.wireformats.Register;

public class MessagingNode implements Node {

	private Socket registrySocket;
	private TCPServerThread serverThread;
	private TCPSender sender;
	private String nodeID;
	private int sendTracker;
	private int receiveTracker;
	private int relayTracker;

	private long sendSummation;
	private long receiveSummation;
	EventFactory eventsF;


	private String registryName;
	private HashMap<Integer, Connection> connections;

	private int connectionCtr;
	
	private String link1;
	private String link2;




	public MessagingNode(String regHost, int regPort) {

		//Try to connect this socket to the port the REGISTRY is listening on 
		try {
			registrySocket = new Socket(regHost, regPort);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		serverThread = null;
		sender = null;
		sendTracker = 0;
		receiveTracker = 0;
		relayTracker = 0;

		sendSummation = 0;
		receiveSummation = 0;

		eventsF = EventFactory.getInstance();

		connections = new HashMap<Integer, Connection>();

		registryName = regHost + ":" + regPort;
		connectionCtr = 0;
		
		link1 = "";
		link2 = "";

	}

	public void run() {

		RegistryConnection();

	}

	private void RegistryConnection(){


		try {

			//start the server on a random port
			serverThread = new TCPServerThread(0, this);
			serverThread.start();

			//start the sender connected to the Registry
			sender = new TCPSender(registrySocket);

			Register registerMsg = (Register) (eventsF.createBlankEvent(Protocol.REGISTER_REQUEST, this));
			sender.sendData(registerMsg.getBytes());


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	private void deregister(){



		try {
			sender = new TCPSender(registrySocket);

			Deregister deregistration = (Deregister)(eventsF.createBlankEvent(Protocol.DEREGISTER_REQUEST, this));
			sender.sendData(deregistration.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setupOverlayConnections(MessagingNodesList lst){
		
		
		String c1[] = lst.connect1.split(":");
		String c2[] = lst.connect2.split(":");
		
		Connection connect1 = null;
		Connection connect2 = null;
		try {
			connect1 = new Connection(this, new Socket(c1[0], Integer.parseInt(c1[1])));
			connect2 = new Connection(this, new Socket(c2[0], Integer.parseInt(c2[1])));

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		boolean c1registered = registerConnection(connect1);
		connectionCtr++;
		boolean c2registered = registerConnection(connect2);
		connectionCtr++;
		
		if(c1registered){

			reverseOverlay(connect1);
			
		}
		if(c2registered){

			reverseOverlay(connect2);
		}

		
		
	}

	private void reverseOverlay(Connection c1){
		ConnectBack msg = null;
		
		try {
			msg =(ConnectBack) eventsF.createBlankEvent(Protocol.CONNECT_BACK, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		msg.setConnection(c1);
		
		
		try {
			c1.sender.sendData(msg.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	synchronized public void onEvent(Event e) {

		int messageType = e.getType();
		//System.out.println("msgtype: " + messageType);

		switch(messageType){
		case Protocol.REGISTER_REQUEST:
			System.err.println("ERROR: Messaging nodes should not receive Registration Requests!");
			break;
		case Protocol.REGISTER_RESPONSE:
			Connection c = new Connection(this, registrySocket);

			if(registerConnection(c)){
				connectionCtr++;
			}
			break;


		case Protocol.DEREGISTER_REQUEST:


		case Protocol.DEREGISTER_RESPONSE:
			DeregisterResponse d = (DeregisterResponse)e;
			if(d.StatusCode == (byte)1){
				try {
					registrySocket.close();
					serverThread.join();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			break;

		case Protocol.LINK_WEIGHTS:
			LinkWeights lw = (LinkWeights)e;
			link1 = lw.linkInfo1;
			link2 = lw.linkInfo2;
			break;

		case Protocol.MESSAGING_NODES_LIST:
			MessagingNodesList mnl = (MessagingNodesList)e;
			setupOverlayConnections(mnl);
			break;
		case Protocol.PULL_TRAFFIC_SUMMARY:

		case Protocol.TASK_COMPLETE:

		case Protocol.TASK_INITIATE:
		case Protocol.TRAFFIC_SUMMARY:
		
		case Protocol.ROUNDS_MESSAGE:
			
		case Protocol.CONNECT_BACK:

		default:
		}

	}

	@Override
	synchronized public boolean registerConnection(Connection c) {
		if(connections.containsKey(c.key)) return false;
		else{	
			connections.put(c.key, c);
			return true;
		}
	}

	@Override
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


	public static void main(String[] args){
		MessagingNode m = new MessagingNode(args[0], Integer.parseInt(args[1]));
		m.run();

		Scanner keyboard = new Scanner (System.in);

		boolean exitFlag = false;
		while(!exitFlag){
			String line = keyboard.nextLine();


			switch(line){
			case "print-shortest-path": 
				System.out.println("Priting!");
				break;
			case "exit-overlay":
				m.deregister();
				keyboard.close();
				exitFlag = true;
				break;
			default:
				System.err.println("ERROR: Invalid command!");
				break;
			}

		}
		System.exit(0);

	}


}
