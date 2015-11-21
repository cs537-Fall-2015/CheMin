package telecom_module;

import java.io.IOException;

import generic.RoverServerRunnable;
import generic.RoverThreadHandler;

public class TelecomServer extends RoverServerRunnable{

	GuiTelecomControlPanel gui = null;

	public TelecomServer(int port) throws IOException {
		super(port);
		gui = new GuiTelecomControlPanel();	
	}

	@Override
	public void run() {
		try{
			while(true) {
				gui.write("Telecom Server -> Waiting for Request");
				getRoverServerSocket().openSocket();
				/*
				 * at this stage, Telecom can receive the following commands:
				 * 		.json file created by chemin_process which it will send
				 */
				gui.write("telecom received message");
				gui.write("telecom sends information to NASA...");
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				gui.write("telecom: information sent succesfully");
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
