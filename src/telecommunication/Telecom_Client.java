package telecommunication;

import generic.RoverClientRunnable;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import json.Constants;
import json.GlobalReader;

import org.json.simple.JSONObject;

public class Telecom_Client extends RoverClientRunnable{
	
	public Telecom_Client(int port, InetAddress host)
			throws UnknownHostException {
		super(port, host);
	}

	@Override
	public void run() {
		try{
			System.out.println(getRoverSocket().getSocket().getPort());
			if(getRoverSocket().getSocket().getPort()==9008){
				ObjectOutputStream outstr=new ObjectOutputStream(getRoverSocket().getSocket().getOutputStream());
				outstr.writeObject("File Received");
				}
		   }	        
        catch (Exception e) {
			e.printStackTrace();
		}		
	}
}