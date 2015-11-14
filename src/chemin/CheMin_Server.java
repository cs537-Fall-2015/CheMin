package chemin;

import java.io.IOException;
import java.io.ObjectInputStream;
import generic.RoverServerRunnable;
import generic.RoverThreadHandler;

public class CheMin_Server extends RoverServerRunnable {

	private static boolean ccuYes = false;
	
	public CheMin_Server(int port) throws IOException {
		super(port);
	}
// Start
	@Override
	public void run() {
		
		try {
			
			while (true) {
				
				System.out.println("CHEMIN Server -> Waiting for Request");
				
				// Creating socket and waiting for client connection or for CCUMain
				getRoverServerSocket().openSocket();
				
				// read from socket to ObjectInputStream object
				ObjectInputStream inputFromAnotherObject = new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				
				// convert ObjectInputStream object to String
				String message=inputFromAnotherObject.readObject().toString();
				Thread t=Thread.currentThread();
				
				//Wait for 7 seconds
				Thread.sleep(7000);
				System.out.println("Message Received is: "+message);
				
				//If thread is interrupted, set the message to power off
				if(t.isInterrupted()){
					message = "power off";
				}
				
				switch(message.toLowerCase()){
				
				// If message is chemin_on, set the CCU to True
					
					break;
				// If power is off,Call the CHEMIN_POWER_OFF function of CHEMIN module
				case "power off":
					System.out.println(" Deleting Thread ..");
					new CheMin_Process().CHEMIN_POWER_OFF();
					break;
				}
				// Stop getting request from CHEMIN_Client and close all the resources
				inputFromAnotherObject.close();
				}
				
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception error) {
			System.out.println("Server: Error: " + error.getMessage());
		}

	}

}


