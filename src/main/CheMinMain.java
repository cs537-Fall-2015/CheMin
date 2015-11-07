package main;

import java.io.IOException;

import generic.RoverThreadHandler;

import power.Power;
import telecommunication.Telecom;
import cheminProcess.CHEMIN_Server;

public class CheMinMain {

	public static void main(String[] args) {
		
		int port_chemin = 9008;
		
		CHEMIN_Server serverChemin=null;
		Power ps=null;
		Telecom ts=null;
		
		try {	
			serverChemin = new CHEMIN_Server(port_chemin);
		}catch (IOException e) {
			e.printStackTrace();
		}
		Thread server_chemin = RoverThreadHandler.getRoverThreadHandler().getNewThread(serverChemin);
		server_chemin.start();
		
		try {
			ps = new Power(9013);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread serverPower=RoverThreadHandler.getRoverThreadHandler().getNewThread(ps);
		serverPower.start();
		
		try {
			ts = new Telecom(9002);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread servertele=RoverThreadHandler.getRoverThreadHandler().getNewThread(ts);
		servertele.start();
	}
}

