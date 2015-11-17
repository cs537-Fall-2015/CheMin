package main;

import java.io.IOException;

import generic.RoverThreadHandler;
import power_module.PowerServer;
import telecom_module.TelecomServer;
import chemin_module.CheminModuleMain;

public class SimulateRoverMain {

	public static void main(String[] args) {

		int chemin_input_port = 9008;
		int chemin_output_port = 9010; //can be modified

		//modules creation
		CheminModuleMain Chemin = null;
		PowerServer Power=null;
		TelecomServer Telecom=null;

		//modules launching
		try {
			Chemin = new CheminModuleMain(chemin_input_port,chemin_output_port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			Power = new PowerServer(9013);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread_power=RoverThreadHandler.getRoverThreadHandler().getNewThread(Power);
		thread_power.start();
		
		try {
			Telecom = new TelecomServer(9002);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread_tele=RoverThreadHandler.getRoverThreadHandler().getNewThread(Telecom);
		thread_tele.start();
	}
}
