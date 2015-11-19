package power_module;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import generic.RoverClientRunnable;

public class PowerClient  extends RoverClientRunnable{
	
	public PowerClient(int port, InetAddress host) throws UnknownHostException {
		super(port, host);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		try{
			System.out.println(getRoverSocket().getSocket().getPort());
			
			// if the port is 9008, get the output string from the rover socket and send message power on to Chemin_Server
			if(getRoverSocket().getSocket().getPort()==9008){
				ObjectOutputStream outstr=new ObjectOutputStream(getRoverSocket().getSocket().getOutputStream());
				outstr.writeObject("power is on");
				}
		   }	        
        catch (Exception e) {
			e.printStackTrace();
		}
	}
}
