package servers;

import java.io.IOException;


public class ServerRun{
	public static void main (String [] args ) throws IOException{
	MultiThreadedServer server = new MultiThreadedServer(13267);
	new Thread(server).start();
	/*
	try {
	    Thread.sleep(50 * 1000);
	} catch (InterruptedException e) {
    	e.printStackTrace();
	}*/
	System.out.println("waiting");
	while(true){}	
	//System.out.println("Stopping Server");
	//server.stop();
	}
}
