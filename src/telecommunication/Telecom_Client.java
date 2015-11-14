package telecommunication;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import generic.RoverClientRunnable;

public class Telecom_Client extends RoverClientRunnable{
	
	public Telecom_Client(int port, InetAddress host)
			throws UnknownHostException {
		super(port, host);
	}

	@Override
	public void run() {
		try{
			System.out.println(getRoverSocket().getSocket().getPort());
			
			// If the port no. is 9008 then create the new output stream and pass the message to Chemin Server
			if(getRoverSocket().getSocket().getPort()==9008){
				ObjectOutputStream outstr=new ObjectOutputStream(getRoverSocket().getSocket().getOutputStream());
				outstr.writeObject("File Received");
				}
		   }	        
        catch (Exception e) {
			e.printStackTrace();
        }
	}
}