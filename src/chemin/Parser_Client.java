package chemin;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

import generic.RoverClientRunnable;
import json.Constants;
import json.GlobalReader;

class Parser_Client extends RoverClientRunnable{

	public Parser_Client(int port, InetAddress host)
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