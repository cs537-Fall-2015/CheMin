// New File

package CCU;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.json.simple.JSONObject;

import json.Constants;
import json.GlobalReader;

import generic.RoverClientRunnable;

public class CCU_Client extends RoverClientRunnable{

	public CCU_Client(int port, InetAddress host)
			throws UnknownHostException {
		super(port, host);
	}

	@Override
	public void run() {
		try{
			System.out.println(getRoverSocket().getPort());
			ObjectOutputStream ostr=new ObjectOutputStream(getRoverSocket().getSocket().getOutputStream());
			GlobalReader gr=new GlobalReader(Constants.ROOT_PATH+"1");
			JSONObject json= gr.getJSONObject();
			ostr.writeObject(json);
			ObjectInputStream instr=new ObjectInputStream(getRoverSocket().getSocket().getInputStream());
			System.out.println(instr.readObject().toString());
		}	 catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (Exception error) {
			System.out.println("Client CCU: Error:" + error.getMessage());
		} 	
	}
}