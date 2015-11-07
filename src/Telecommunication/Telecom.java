package telecommunication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import generic.RoverClientRunnable;
import generic.RoverServerRunnable;
import generic.RoverThreadHandler;

public class Telecom extends RoverServerRunnable {

	public Telecom(int port) throws IOException {
		super(port);
	}

	@Override
	public void run() {
		while(true)
		try {				
				System.out.println("Telecom Server -> Waiting for Request");
				getRoverServerSocket().openSocket();
				ObjectInputStream oinstr=new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				String message=oinstr.readObject().toString();
				System.out.println(message);
				Telecom_Client teleclient=new Telecom_Client(9008, null);
				Thread cpower=RoverThreadHandler.getRoverThreadHandler().getNewThread(teleclient);
				cpower.start();
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Telecom ps=null;
		try {
			ps = new Telecom(9002);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread serverPower=RoverThreadHandler.getRoverThreadHandler().getNewThread(ps);
		serverPower.start();
	}

}

class Telecom_Client extends RoverClientRunnable{
	
	public Telecom_Client(int port, InetAddress host)
			throws UnknownHostException {
		super(port, host);
	}

	@Override
	public void run() {
		try{
			System.out.println(getRoverSocket().getSocket().getPort());
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
