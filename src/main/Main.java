package main;

import java.io.IOException;
import generic.RoverThreadHandler;
import power.Power_Server;
import telecommunication.Telecom_Server;

public class Main {

	public static void main(String[] args) {

		// Defining a port for CheMin
		int port_chemin = 9008;

		try {
			
			// Create a thread for CHEMIN SERVER
			chemin.CheMin_Server serverChemin = new chemin.CheMin_Server(port_chemin);
			Thread server_chemin = RoverThreadHandler.getRoverThreadHandler().getNewThread(serverChemin);
			server_chemin.start();
		}catch (IOException e) {
			e.printStackTrace();
		}

		// Create a thread for Power SERVER
		Power_Server ps=null;
		try {
			ps = new Power_Server(9013);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Thread serverPower=RoverThreadHandler.getRoverThreadHandler().getNewThread(ps);
		serverPower.start();
	
		// Create a thread for CHEMIN SERVER
		Telecom_Server ts=null;
		try {
			ts = new Telecom_Server(9002);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread servertele=RoverThreadHandler.getRoverThreadHandler().getNewThread(ts);
		servertele.start();
	}
}