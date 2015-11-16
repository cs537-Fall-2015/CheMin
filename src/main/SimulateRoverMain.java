package main;

import java.io.IOException;

import generic.RoverThreadHandler;
import power.Power;
import power_module.PowerServer;
import telecommunication.Telecom;
import chemin_module.CheminModuleMain;

public class SimulateRoverMain {

	public static void main(String[] args) {

		int chemin_input_port = 9008;
		int chemin_output_port = ???;

		//modules creation
		CheminModuleMain Chemin = null;
		PowerServer Power=null;
//		Telecom telecom=null;

		//modules launching
		Chemin = new CheminModuleMain(chemin_input_port,chemin_output_port);
		
		try {
			Power = new PowerServer(9013);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread_power=RoverThreadHandler.getRoverThreadHandler().getNewThread(power);
		thread_power.start();
/*
		try {
			telecom = new Telecom(9002);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread_tele=RoverThreadHandler.getRoverThreadHandler().getNewThread(telecom);
		thread_tele.start();
*/
}

