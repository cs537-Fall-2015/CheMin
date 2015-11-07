package cheminServer;

import java.io.IOException;
import java.io.ObjectInputStream;

import cheminProcess.Chemin_process;
import generic.RoverServerRunnable;
import generic.RoverThreadHandler;


public class CHEMIN_Server extends RoverServerRunnable {

	private static boolean ccuYes = false;
	Chemin_process processChemin=null;
	
	public CHEMIN_Server(int port) throws IOException {
		super(port);
		
		//Creates Chemin process thread 
		try {
			processChemin = new Chemin_process(9018);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread_process_chemin=RoverThreadHandler.getRoverThreadHandler().getNewThread(processChemin);
		thread_process_chemin.start();
	}

	@Override
	public void run() {

		try {

			while (true) {

				System.out.println("CHEMIN Server -> Waiting for Request");

				// creating socket and waiting for client connection or for CCU
				getRoverServerSocket().openSocket();

				// read from socket to ObjectInputStream object
				ObjectInputStream inputFromAnotherObject = new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());

				// convert ObjectInputStream object to String
				String message=inputFromAnotherObject.readObject().toString();
				Thread t=Thread.currentThread();
				
				//wait for 7 sec
				Thread.sleep(7000);
				System.out.println("Message Received is: "+message);
				
				//If thread is interrupted, set the message to power off
				if(t.isInterrupted()){
					message = "power off";
				}

				switch(message.toLowerCase()){
				
				// If message is chemin_on, set the CCU to True
				case "chemin_on":
					ccuYes = true;
					System.out.println("CCU has send request to CHEMIN to turn on");
					System.out.println("CHEMIN is requesting power and Sending a json file with power Requirements to Power group");
!!!!!!!!!!!!!!!!!!! SEND MESSAGE TO CHEMIN PROCESS TO ASK FOR LAUNCHING
					break;
				
				//If message is power on, start the chemin process else print the required message 
				case "power on":
					boolean process = false;
					if(ccuYes){
!!!!!!!!!!!!!!!!!!!		 SEND MESSAGE TO POWER TO ASK FOR TURN ON
					}else{
						System.out.println(" CHEMIN dont have permission from parser ,Yet !");
					}

					break;

				//If process is true, 
				case "power off":
					System.out.println(" Deleting Thread ..");
!!!!!!!!!!!!!!!!!!! SEND MESSAGE TO POWER TO ASK FOR TURN OFF
					break;
				}
				// close resources
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
