package chemin_module;

import java.io.IOException;

import cheminProcess.Chemin_process;
import cheminServer.CHEMIN_Server;
import generic.RoverServerRunnable;
import generic.RoverThreadHandler;

public class CheminModuleMain{

	private CheminClient client=null;
	private CheminProcess process=null;
	private CheminServer server=null;
	
	//module constructor
	public CheminModuleMain(int input_port, int output_port) throws IOException {
		
		//Create and run server thread 
		try {	
			server = new CheminServer(input_port);
		}catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread_server_chemin = RoverThreadHandler.getRoverThreadHandler().getNewThread(server);
		thread_server_chemin.start();
		
		//Create and run process thread 
		try {
			process = new CheminProcess(9010); //port can be modified
		} catch (IOException e) { 
			e.printStackTrace();
		}
		Thread thread_process_chemin=RoverThreadHandler.getRoverThreadHandler().getNewThread(process);
		thread_process_chemin.start();
		thread_process_chemin.interrupt();
		
/*		//Create and run client thread 
		try {
			client = new CheminClient(output_port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread thread_client_chemin=RoverThreadHandler.getRoverThreadHandler().getNewThread(client);
		thread_client_chemin.start();
	}
*/
}
