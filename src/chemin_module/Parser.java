package chemin_module;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import generic.RoverClientRunnable;

public class Parser extends RoverClientRunnable{
	private ObjectOutputStream outstr=null;
	
	public Parser(int internal_chemin_port, InetAddress host) throws UnknownHostException {
		super(internal_chemin_port, host);

		//create socket to connect to cheminProcess
		Socket socket = null;
		try {
			socket = new Socket("localhost",internal_chemin_port);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		//creates the stream to ouput through the socket to cheminProcess
		try {
			outstr = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException ey) {
			ey.printStackTrace();
		}
	}

	private static boolean powerOn = false;
	
	/*
	 * returns 0 if message is wrong 
	 * returns 1 if message has correctly been sent to chemin process
	 * returns 2 if message should be sent to power
	 * returns 3 if message should be sent to telecom
	 */
	public int check_message(String message) {
		switch(message.toLowerCase()) {	
			/*
			 * INTERNAL COMMUNICATION
			 */
			// If message is chemin_on, set the CCU to True
			case "full process":
				System.out.println("CCU has send request to CHEMIN to turn on");
				System.out.println("CHEMIN is requesting power and Sending a json file with power Requirements to Power group");
				//SEND MESSAGE TO CHEMIN PROCESS TO ASK FOR LAUNCHING
				// can only be done if power has been turned on
				if(powerOn){
					//send message through socket to cheminProcess
					try {
						outstr.writeObject(message.toLowerCase());
					} catch (IOException ez) {
						ez.printStackTrace();
					}
				} else{
					System.out.println(" CHEMIN is not turned on ,Yet !");
				}
				return 1;
				
			/*
			* EXTERNAL COMMUNICATION
			*/
			//If message is power on, start the chemin process else print the required message 
			case "power on":		
				powerOn = true;
				return 2;
			//If process is true, 
			case "power off":
				System.out.println(" Deleting Thread ..");
				return 2;
			default:
				return 0;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
