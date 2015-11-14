package telecommunication;

import java.io.IOException;
import java.io.ObjectInputStream;
import generic.RoverServerRunnable;
import generic.RoverThreadHandler;

public class Telecom_Server extends RoverServerRunnable {

	public Telecom_Server(int port) throws IOException {
		super(port);
	}

	@Override
	public void run() {
		while(true)
		try {				
			// start reading message and print the message
				System.out.println("Telecom Server -> Waiting for Request");
				getRoverServerSocket().openSocket();
				ObjectInputStream oinstr=new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				String message=oinstr.readObject().toString();
				System.out.println(message);
				
				// create Telecom_Client and make the client to listen on port no. 9008
				Telecom_Client teleclient=new Telecom_Client(9008, null);
				
				// Get the thread from RoverThreadHandler for that Client and start the thread
				Thread cpower=RoverThreadHandler.getRoverThreadHandler().getNewThread(teleclient);
				cpower.start();
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Telecom_Server ps=null;
		try {
			// start the telecom process on port no. 9002
			ps = new Telecom_Server(9002);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// get the thread for power server and start the thread
		Thread serverPower=RoverThreadHandler.getRoverThreadHandler().getNewThread(ps);
		serverPower.start();
	}

}