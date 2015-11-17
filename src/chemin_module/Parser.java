package chemin_module;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

import generic.RoverClientRunnable;
import generic.RoverThreadHandler;
import json.Constants;
import json.GlobalReader;

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
	private static boolean startRequested = false;
	/*
	 * returns 0 if message is wrong 
	 * returns 1 if message is internal
	 * returns 2 if message is external
	 */
	public int check_message(String message) {
		switch(message.toLowerCase()) {	
		/*
		 * INTERNAL COMMUNICATION
		 */
		// If message is chemin_on, set the CCU to True
		case "power is on":
			//SEND MESSAGE TO CHEMIN PROCESS TO ASK FOR LAUNCHING
			// can only be done if power has been turned on
			powerOn = true;
			if(startRequested) {
				startRequested=false;
				//send message through socket to cheminProcess
				try {
					outstr.writeObject("full process");
				} catch (IOException ez) {
					ez.printStackTrace();
				}
			} else{
				System.out.println(" CHEMIN is not turned on ,Yet !");
			}
			return 1;
		case "ALL EXTRA COMMANDS SHOULD BE LIKE THAT":
			if(powerOn) {
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
		case "start":	
			startRequested = true;
			System.out.println("client has send request to CHEMIN to turn on");
			System.out.println("CHEMIN is requesting power and Sending a json file with power Requirements to Power group");
			//launch chemin client to create communication with power module
			CheminClient powerclient = null;
			try {
				powerclient = new CheminClient(9013,null);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			Thread powerclientthread=RoverThreadHandler.getRoverThreadHandler().getNewThread(powerclient);
			powerclientthread.start();
			return 2;
		case "telecom ack":	
			System.out.println("chemin receives telecom acknowledge");
			return 1;
		default:
			return 0;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
