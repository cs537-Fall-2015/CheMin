package power;

import generic.RoverServerRunnable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Power extends RoverServerRunnable {

	public Power(int port) throws IOException {
		super(port);
	}
	@Override
	public void run() {
		try {				
			System.out.println("Power Server -> Waiting for Request");
			getRoverServerSocket().openSocket();
			/*
			 * at this stage, Power can receive the following commands:
			 * 		POWER_ON	:	turns the power ON and send a message to CHEMIN_process to say that power is set 
			 * 		POWER_OFF 	:	turns the power OFF and send a message to CHEMIN_process to say that power is shut
			 */
			ObjectInputStream oinstr=new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
			String message=oinstr.readObject().toString();
			System.out.println(message);
			send_power_on_to_chemin_process();

		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void send_power_on_to_chemin_process() {
		try{
			System.out.println(getRoverServerSocket().getSocket().getPort());
			if(getRoverServerSocket().getSocket().getPort()==9008){
				ObjectOutputStream outstr=new ObjectOutputStream(getRoverServerSocket().getSocket().getOutputStream());
				outstr.writeObject("power on");
			}
		}	        
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

