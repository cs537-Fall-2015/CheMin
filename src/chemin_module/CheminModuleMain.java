package chemin_module;

import java.io.IOException;

import generic.RoverThreadHandler;

public class CheminModuleMain{

	private CheminProcess process=null;
	private CheminServer server=null;

	//module constructor
	public CheminModuleMain(int input_port) throws IOException {

		try {
			process = new CheminProcess(9010); //port can be modified
		} catch (IOException e) { 
			e.printStackTrace();
		}
		//Create and run server thread 
		try {	
			server = new CheminServer(input_port, process);
			
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread_server_chemin = RoverThreadHandler.getRoverThreadHandler().getNewThread(server);
		thread_server_chemin.start();
		
		//thread_server_chemin.interrupt();

		//Create and run process thread 
		
		Thread thread_process_chemin=RoverThreadHandler.getRoverThreadHandler().getNewThread(process);
		thread_process_chemin.start();
		thread_process_chemin.interrupt();
	}
}
