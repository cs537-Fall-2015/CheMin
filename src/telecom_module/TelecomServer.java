package telecom_module;

import java.io.IOException;
import java.io.ObjectInputStream;

import generic.RoverServerRunnable;
import generic.RoverThreadHandler;

public class TelecomServer extends RoverServerRunnable{

	public TelecomServer(int port) throws IOException {
		super(port);
	}

	@Override
	public void run() {
		try{
			while(true) {
				System.out.println("Telecom Server -> Waiting for Request");
				getRoverServerSocket().openSocket();
				/*
				 * at this stage, Telecom can receive the following commands:
				 * 		.json file created by chemin_process which it will send
				 */
				System.out.println("telecom received message");
				System.out.println("telecom sends information to NASA...");
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("telecom: information sent succesfully");
				// Create the new Power_Client and make the client to listen on port no. 9008
				TelecomClient tclient=new TelecomClient(9008,null);

				// Get the thread for that client and start the thread
				Thread ctelec=RoverThreadHandler.getRoverThreadHandler().getNewThread(tclient);
				ctelec.start();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
