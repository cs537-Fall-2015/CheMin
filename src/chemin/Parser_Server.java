package chemin;

import java.io.IOException;
import generic.RoverThreadHandler;
import generic.RoverServerRunnable;

public class Parser_Server extends RoverServerRunnable {
	public Parser_Server(int port) throws IOException {
		super(port);
	}

	@Override
	public void run() {
		
		while(true){
		try {
				System.out.println("CCU is trying to turn on CHEMIN");
				//creating socket and waiting for client connection			
				Parser_Client ccuserver=new Parser_Client(9008,null);
				
				// get thread for ccu server and start the thread fr cheimin_client
				Thread cheminclient=RoverThreadHandler.getRoverThreadHandler().getNewThread(ccuserver);
				cheminclient.start();
				getRoverServerSocket().openSocket();
				
		}catch(Exception e){
			e.printStackTrace();
		}
		}
	}

		
	public static void main(String[] args) {
		// Define CCU port for 9008 
		int ccuport=9008;
		Parser_Server ccuserver=null;
		try {
			// Create new CCU server port
			ccuserver = new Parser_Server(ccuport);
		} catch (IOException e) {
		  e.printStackTrace();
		}
		// get the thread for ccuserver and start it
		Thread serverccu=RoverThreadHandler.getRoverThreadHandler().getNewThread(ccuserver);
		serverccu.start();
	}
}