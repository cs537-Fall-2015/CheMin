package chemin_module;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import generic.RoverClientRunnable;

public class Parser extends RoverClientRunnable{
	
	public Parser(int internal_chemin_port, InetAddress host) throws UnknownHostException {
		super(internal_chemin_port, host);
		// TODO Auto-generated constructor stub
	}

	private static boolean powerOn = false;
	

	public void check_message(String message) {
		switch(message.toLowerCase()) {	
		/*
		 * INTERNAL COMMUNICATION
		 */
			// If message is chemin_on, set the CCU to True
			case "full process":
				System.out.println("CCU has send request to CHEMIN to turn on");
				System.out.println("CHEMIN is requesting power and Sending a json file with power Requirements to Power group");
	//	!!!!!!!!!!!!!!!!!!! SEND MESSAGE TO CHEMIN PROCESS TO ASK FOR LAUNCHING
				Socket socket = null;
				try {
					socket = new Socket("localhost",??????);
				} catch (IOException ex) {
				ex.printStackTrace();
				}
				ObjectOutputStream outstr=null;
				try {
					outstr = new ObjectOutputStream(socket.getOutputStream());
				} catch (IOException ey) {
				ey.printStackTrace();
				}
				try {
					outstr.writeObject(msg);
				} catch (IOException ez) {
				ez.printStackTrace();
				}
				powerOn = true;
				break;
		/*
		* EXTERNAL COMMUNICATION
		*/
			//If message is power on, start the chemin process else print the required message 
			case "power on":
				boolean process = false;
				if(powerOn){
		!!!!!!!!!!!!!!!!!!!		 SEND MESSAGE TO POWER TO ASK FOR TURN ON
				} else{
					System.out.println(" CHEMIN dont have permission from parser ,Yet !");
				}
	
				break;
			//If process is true, 
			case "power off":
				System.out.println(" Deleting Thread ..");
		!!!!!!!!!!!!!!!!!!! SEND MESSAGE TO POWER TO ASK FOR TURN OFF
				break;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
