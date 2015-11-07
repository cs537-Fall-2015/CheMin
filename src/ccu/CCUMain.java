package ccu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

import generic.RoverThreadHandler;
import generic.RoverServerRunnable;
import generic.RoverClientRunnable;

import json.Constants;
import json.GlobalReader;

public class CCUMain {
		
	public static void main(String[] args) {
		// Define CCU port for 9008 
		int ccuport=9008;
		CCU_Server ccuserver=null;
		try {
			// Create new CCU server port
			ccuserver = new CCU_Server(ccuport);
		} catch (IOException e) {
		  e.printStackTrace();
		}
		// get the thread for ccuserver and start it
		Thread serverccu=RoverThreadHandler.getRoverThreadHandler().getNewThread(ccuserver);
		serverccu.start();
	}
}

class CCU_Server extends RoverServerRunnable {
	public CCU_Server(int port) throws IOException {
		super(port);
	}

	@Override
	public void run() {
		
		while(true){
		try {
				System.out.println("CCU is trying to turn on CHEMIN");
				//creating socket and waiting for client connection			
				CCU_Client ccuserver=new CCU_Client(9008,null);
				
				// get thread for ccu server and start the thread fr cheimin_client
				Thread cheminclient=RoverThreadHandler.getRoverThreadHandler().getNewThread(ccuserver);
				cheminclient.start();
				getRoverServerSocket().openSocket();
				
		}catch(Exception e){
			e.printStackTrace();
		}
		}
	}
}

class CCU_Client extends RoverClientRunnable{

	public CCU_Client(int port, InetAddress host)
			throws UnknownHostException {
		super(port, host);
	}

	@Override
	public void run() {
		try{
			
			// Call the getPort method on the object returned by getSocket method
			System.out.println(getRoverSocket().getPort());
			ObjectOutputStream ostr=new ObjectOutputStream(getRoverSocket().getSocket().getOutputStream());
			
			// read that object from GlobalReader
			GlobalReader gr=new GlobalReader(Constants.ROOT_PATH+"1");
			
			// send that json object by output string to CHEMIN_Server
			JSONObject json= gr.getJSONObject();
			ostr.writeObject(json);
			ObjectInputStream instr=new ObjectInputStream(getRoverSocket().getSocket().getInputStream());
			System.out.println(instr.readObject().toString());
		}	 catch (UnknownHostException e) {
			e.printStackTrace();
		}
		// else print an error message
		catch (Exception error) {
			System.out.println("Client CCU: Error:" + error.getMessage());
		} 	
	}
}