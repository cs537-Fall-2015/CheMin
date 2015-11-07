package cheminServer;

import java.io.IOException;
import java.io.ObjectInputStream;

import cheminProcess.CHEMIN;
import cheminProcess.CHEMIN_Client;
import generic.RoverServerRunnable;
import generic.RoverThreadHandler;


public class CHEMIN_Server extends RoverServerRunnable {

		private static boolean ccuYes = false;
		
		public CHEMIN_Server(int port) throws IOException {
			super(port);
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
					Thread.sleep(7000);
					System.out.println("Message Received is: "+message);
					if(t.isInterrupted()){
						message = "power off";
					}
					
					switch(message.toLowerCase()){
					case "chemin_on":
						ccuYes = true;
						System.out.println("CCU has send request to CHEMIN to turn on");
						System.out.println("CHEMIN is requesting power and Sending a json file with power Requirements to Power group");
						CHEMIN_Client client=new CHEMIN_Client(9013,null);
						Thread power=RoverThreadHandler.getRoverThreadHandler().getNewThread(client);
						power.start();
						break;
					
					case "power on":
						
						boolean process = false;
						if(ccuYes){
							CHEMIN chemin = new CHEMIN();
							process=chemin.CHEMIN_Process();
						}else{
							System.out.println(" CHEMIN dont have permission from CCU ,Yet !");
						}
							
						if(process){
							CHEMIN_Client teleclient=new CHEMIN_Client(9002,null);
							Thread telecommunication=RoverThreadHandler.getRoverThreadHandler().getNewThread(teleclient);
							telecommunication.start();
							
						}
						
						break;
					
					case "power off":
						System.out.println(" Deleting Thread ..");
						new CHEMIN().CHEMIN_POWER_OFF();
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
