package main;

import generic.RoverThreadHandler;

import java.io.IOException;

import module1.ModuleOneClient;
import module1.ModuleOneServer;
import module2.ModuleTwoClient;
import module2.ModuleTwoServer;

public class MasterMain {

	public static void main(String[] args) {
		
		//Each module has its own port
		int port_one = 9897;
		int port_two = 9898;
		
		try {
			
			// create a thread for module one
			ModuleOneServer serverOne = new ModuleOneServer(port_one);
			Thread server_1 = RoverThreadHandler.getRoverThreadHandler().getNewThread(serverOne);
			
			// create a thread for module two
			ModuleTwoServer serverTwo = new ModuleTwoServer(port_two);
			Thread server_2 = RoverThreadHandler.getRoverThreadHandler().getNewThread(serverTwo);
			
			// each server begins listening
			server_1.start();
			server_2.start();
			
			// The following commands are examples of sending data: 
			// from module 1 client to module 2 server
			// and from module 2 client to module 2 server
			
			// client one server sending messages to server two
			ModuleOneClient clientOne = new ModuleOneClient(port_two, null); // notice port_two
			Thread client_1 = RoverThreadHandler.getRoverThreadHandler().getNewThread(clientOne);
			
			// client two server sending messages to server one
			ModuleTwoClient clientTwo = new ModuleTwoClient(port_one, null); // notice port_one
			Thread client_2 = RoverThreadHandler.getRoverThreadHandler().getNewThread(clientTwo);
			
			// start the thread which communicates through sockets
			client_1.start();
			client_2.start();
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
