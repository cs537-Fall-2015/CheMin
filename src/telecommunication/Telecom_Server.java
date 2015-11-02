package telecommunication;

import generic.RoverServerRunnable;
import generic.RoverThreadHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import json.Constants;
import json.MyWriter;

public class Telecom_Server extends RoverServerRunnable {

	public Telecom_Server(int port) throws IOException {
		super(port);
	}

	@Override
	public void run() {
		while(true)
		try {				
				System.out.println("Telecom Server -> Waiting for Request");
				getRoverServerSocket().openSocket();
				ObjectInputStream oinstr=new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				String message=oinstr.readObject().toString();
				System.out.println(message);
				Telecom_Client teleclient=new Telecom_Client(9008, null);
				Thread cpower=RoverThreadHandler.getRoverThreadHandler().getNewThread(teleclient);
				cpower.start();
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}