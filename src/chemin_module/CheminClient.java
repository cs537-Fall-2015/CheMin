package chemin_module;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

import generic.RoverClientRunnable;
import json.Constants;
import json.GlobalReader;


public class CheminClient extends RoverClientRunnable {

	private String message_to_send = null;

	public CheminClient(int port, InetAddress host) throws UnknownHostException {
		super(port, host);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		try{
			// Check if the value is returned by the getPort = 9013
			if(getRoverSocket().getSocket().getPort()==9013){
				ObjectOutputStream outstr=new ObjectOutputStream(getRoverSocket().getSocket().getOutputStream());
				System.out.println("CHEMIN-->Power group");
				System.out.println("CHEMIN-->Sending you my requirements in a json file");

				// Read the power requirement from GlobalReader and convert that into json object
				GlobalReader gr=new GlobalReader(Constants.ROOT_PATH+"PowerRequirement");

				// send that json object by output string to CHEMIN_Server
				JSONObject json= gr.getJSONObject();
				outstr.writeObject(json);
			}
			
			
			// If the value returned by getPort method = 9002
			if(getRoverSocket().getSocket().getPort()==9002){

				System.out.println("contacting to tele success");
				// Create new object and read image from Rover socket
				ObjectOutputStream ostr=new ObjectOutputStream(getRoverSocket().getSocket().getOutputStream());

				// write the image object
				ostr.writeObject("Chemin--> telecommunication: Read this image json");
				GlobalReader gr2=new GlobalReader(Constants.ROOT_PATH+"XrdDiffraction");

				// Send json object to Server
				JSONObject jsonTele= gr2.getJSONObject();
				ostr.writeObject(jsonTele);
			}

		}	        
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void send(String message) {
		message_to_send = message;
	}

}
