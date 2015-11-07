package main;

import java.io.IOException;

import cheminServer.CHEMIN_Server;
import generic.RoverThreadHandler;
import cheminProcess.Chemin_process;
import power.Power;
import telecommunication.Telecom;

public class CheMinMain {

	public static void main(String[] args) {

		int port_chemin = 9008;

		CHEMIN_Server serverChemin=null;
		Power power=null;
		Telecom telecom=null;

		try {	
			serverChemin = new CHEMIN_Server(port_chemin);
		}catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread_server_chemin = RoverThreadHandler.getRoverThreadHandler().getNewThread(serverChemin);
		thread_server_chemin.start();

		try {
			power = new Power(9013);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread_power=RoverThreadHandler.getRoverThreadHandler().getNewThread(power);
		thread_power.start();

		try {
			telecom = new Telecom(9002);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread_tele=RoverThreadHandler.getRoverThreadHandler().getNewThread(telecom);
		thread_tele.start();
}

