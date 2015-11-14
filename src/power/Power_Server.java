package power;

import java.io.IOException;
import java.io.ObjectInputStream;
import generic.RoverServerRunnable;
import generic.RoverThreadHandler;

public class Power_Server extends RoverServerRunnable {

	public Power_Server(int port) throws IOException {
		super(port);
	}
	@Override
	public void run() {
		try {			
			// Start listening from the socket and read the message
				System.out.println("Power Server -> Waiting for Request");
				getRoverServerSocket().openSocket();
				ObjectInputStream oinstr=new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				String message=oinstr.readObject().toString();
				System.out.println(message);
				
				// Create the new Power_Client and make the client to listen on port no. 9008
				Power_Client pclient=new Power_Client(9008,null);
				
				// Get the thread for that client and start the thread
				Thread cpower=RoverThreadHandler.getRoverThreadHandler().getNewThread(pclient);
				cpower.start();
		
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		// initializing port no. 9013
		int powerport = 9013;
		Power_Server powerserver=null;
		try {
			// Create the new power port to listen power server
			powerserver = new Power_Server(powerport);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// get the thread for power server and start the thread
		Thread serverPower=RoverThreadHandler.getRoverThreadHandler().getNewThread(powerserver);
		serverPower.start();
	}
}