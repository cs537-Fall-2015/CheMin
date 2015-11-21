package power_module;

import java.io.IOException;
import java.io.ObjectInputStream;

import generic.RoverServerRunnable;
import generic.RoverThreadHandler;

public class PowerServer extends RoverServerRunnable {

	GuiPowerControlPanel gui=null;
	
	public PowerServer(int port) throws IOException {
		super(port);
		gui=new GuiPowerControlPanel();
	}

	@Override
	public void run() {
		try {	
			while(true){
				// Start listening from the socket and read the message
				gui.write("Power Server -> Waiting for Request");
				PowerClient pclient=new PowerClient(9008,null);

				// Get the thread for that client and start the thread
				Thread cpower=RoverThreadHandler.getRoverThreadHandler().getNewThread(pclient);
				cpower.start();
				getRoverServerSocket().openSocket();
				ObjectInputStream oinstr=new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				String message=oinstr.readObject().toString();
				gui.write(message);

				// Create the new Power_Client and make the client to listen on port no. 9008
				
			}
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}

}
