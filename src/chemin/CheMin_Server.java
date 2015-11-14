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
<<<<<<< HEAD
<<<<<<< HEAD
				case "chemin_on":
					ccuYes = true;
					System.out.println("CCU has send request to CHEMIN to turn on");
					System.out.println("CHEMIN is requesting power and Sending a json file with power Requirements to Power group");
					CheMin_Client client=new CheMin_Client(9013,null);
					Thread power=RoverThreadHandler.getRoverThreadHandler().getNewThread(client);
					power.start();
					break;
				// If message is power on, start the chemin process else print the required message 
				case "power on":
					
					boolean process = false;
					if(ccuYes){
						CheMin_Process chemin = new CheMin_Process();
						process=chemin.CHEMIN_Process();
					}else{
						System.out.println(" CHEMIN dont have permission from CCU ,Yet !");
					}
					// 	If process is true, Create the new CHEMIN_Client and make the client to listen on port no. 9002
					if(process){
						CheMin_Client teleclient=new CheMin_Client(9002,null);
						Thread telecommunication=RoverThreadHandler.getRoverThreadHandler().getNewThread(teleclient);
						telecommunication.start();
						
					}
					
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
			
			// case for power interrupt : added to be later
=======
					
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
>>>>>>> refs/heads/master
=======
				case "chemin_on":
					ccuYes = true;
					System.out.println("CCU has send request to CHEMIN to turn on");
					System.out.println("CHEMIN is requesting power and Sending a json file with power Requirements to Power group");
					CheMin_Client client=new CheMin_Client(9013,null);
					Thread power=RoverThreadHandler.getRoverThreadHandler().getNewThread(client);
					power.start();
					break;
				// If message is power on, start the chemin process else print the required message 
				case "power on":
					
					boolean process = false;
					if(ccuYes){
						CheMin_Process chemin = new CheMin_Process();
						process=chemin.CHEMIN_Process();
					}else{
						System.out.println(" CHEMIN dont have permission from CCU ,Yet !");
					}
					// 	If process is true, Create the new CHEMIN_Client and make the client to listen on port no. 9002
					if(process){
						CheMin_Client teleclient=new CheMin_Client(9002,null);
						Thread telecommunication=RoverThreadHandler.getRoverThreadHandler().getNewThread(teleclient);
						telecommunication.start();
						
					}
					
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
>>>>>>> refs/heads/rajnish
				
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception error) {
			System.out.println("Server: Error: " + error.getMessage());
		}

	}

}


