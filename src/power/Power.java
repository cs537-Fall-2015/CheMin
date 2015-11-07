package power;

import generic.RoverServerRunnable;
import generic.RoverThreadHandler;
import generic.RoverClientRunnable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Power extends RoverServerRunnable {

	public Power(int port) throws IOException {
		super(port);
	}
	@Override
	public void run() {
		try {				
				System.out.println("Power Server -> Waiting for Request");
				getRoverServerSocket().openSocket();
				ObjectInputStream oinstr=new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				String message=oinstr.readObject().toString();
				System.out.println(message);
				Power_Client pclient=new Power_Client(9008,null);
				Thread cpower=RoverThreadHandler.getRoverThreadHandler().getNewThread(pclient);
				cpower.start();
		
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public void send_power_on_to_chemin_process() {
		try{
			System.out.println(getRoverSocket().getSocket().getPort());
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

