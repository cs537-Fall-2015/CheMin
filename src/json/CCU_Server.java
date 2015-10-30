package CCU;

import java.io.IOException;
import java.net.ServerSocket;

import generic.RoverServerRunnable;
import generic.RoverThreadHandler;

public class CCU_Server extends RoverServerRunnable {
	public CCU_Server(int port) throws IOException {
		super(port);
	}

	@Override
	public void run() {
		
		while(true){
		try {
				System.out.println("CCU is trying to turn on CHEMIN");
				//creating socket and waiting for client connection			
				CCU_Client ccuserver=new CCU_Client(9008,null);
				Thread cheminclient=RoverThreadHandler.getRoverThreadHandler().getNewThread(ccuserver);
				cheminclient.start();
				getRoverServerSocket().openSocket();
				
		}catch(Exception e){
			e.printStackTrace();
		}
		}
	}
}