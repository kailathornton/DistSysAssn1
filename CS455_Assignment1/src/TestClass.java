import java.net.InetAddress;
import java.net.UnknownHostException;

import cs455.overlay.node.MessagingNode;
import cs455.overlay.node.Registry;


public class TestClass {

	public static void main(String[] args){
		
		Registry registry = new Registry(Integer.parseInt(args[0]));
				
		System.out.println("****************************");
	
		
		
		MessagingNode node1 = new MessagingNode();
		node1.run();
		
//		System.out.println("****************************");
//		
//		System.out.println("NODE 2");
//		MessagingNode node2 = new MessagingNode();
//		node2.run();
		
		
		
	}
	
	
}
