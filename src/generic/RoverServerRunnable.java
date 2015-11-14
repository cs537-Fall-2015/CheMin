package generic;

import java.io.IOException;

public abstract class RoverServerRunnable implements Runnable{
	
	private RoverServerSocket roverServerSocket;
	
	
	public RoverServerRunnable(int port) throws IOException{
		setRoverServerSocket(port);
	}
	
	public void closeAll() throws IOException{		
		if(roverServerSocket != null)
			roverServerSocket.closeAll();
	}
	
	public RoverServerSocket getRoverServerSocket() {
		return roverServerSocket;
	}

	private void setRoverServerSocket(int port) throws IOException {
		this.roverServerSocket = new RoverServerSocket(port);
	}
		
}
