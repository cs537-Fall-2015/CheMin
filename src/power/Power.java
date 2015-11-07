package power;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import generic.RoverClientRunnable;
import generic.RoverServerRunnable;
import generic.RoverThreadHandler;

public class Power extends RoverServerRunnable {

	public Power(int port) throws IOException {
		super(port);
	}
	@Override
	public void run() {
		try {			
			// Start listening from the socket and read the message
				System.out.println("Power Server -> Waiting for Request");
				getRoverServerSocket().openSocket();
				ObjectInputStream oinstr=new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				String message=oinstr.readObject().toString();
				System.out.println(message);
				
				// Create the new Power_Client and make the client to listenon port no. 9008
				Power_Client pclient=new Power_Client(9008,null);
				
				// Get the thread for that client and start the thread
				Thread cpower=RoverThreadHandler.getRoverThreadHandler().getNewThread(pclient);
				cpower.start();
		
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		// initializing port no. 9013
		int powerport = 9013;
		Power powerserver=null;
		try {
			// Create the new power port to listen power server
			powerserver = new Power(powerport);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// get the thread for power server and start the thread
		Thread serverPower=RoverThreadHandler.getRoverThreadHandler().getNewThread(powerserver);
		serverPower.start();
	}
}

class Power_Client extends RoverClientRunnable{
	
	public Power_Client(int port, InetAddress host)
			throws UnknownHostException {
		super(port, host);
	}

	@Override
	public void run() {
		try{
			System.out.println(getRoverSocket().getSocket().getPort());
			
			// if the port is 9008, get the output string from the rover socket and send message power on to Chemin_Server
			if(getRoverSocket().getSocket().getPort()==9008){
				ObjectOutputStream outstr=new ObjectOutputStream(getRoverSocket().getSocket().getOutputStream());
				outstr.writeObject("power on");
				}
		   }	        
        catch (Exception e) {
			e.printStackTrace();
		}
	}
}