package telecommunication;

import generic.RoverServerRunnable;

import java.io.IOException;
import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;

public class Telecom extends RoverServerRunnable {

	public Telecom(int port) throws IOException {
		super(port);
	}

	public void run() {
		while(true)
			try {				
				System.out.println("Telecom Server -> Waiting for Request");
				getRoverServerSocket().openSocket();
				/*
				 * at this stage, Telecom can receive the following commands:
				 * 		.json file created by chemin_process which it will send
				 */
				ObjectInputStream oinstr=new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				String message=oinstr.readObject().toString();
				System.out.println(message);
				send_file_received_ack_to_chemin();
			}catch(IOException e){
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}

	public void send_file_received_ack_to_chemin() {
		try{
			System.out.println(getRoverServerSocket().getSocket().getPort());
			if(getRoverServerSocket().getSocket().getPort()==9008){
				ObjectOutputStream outstr=new ObjectOutputStream(getRoverServerSocket().getSocket().getOutputStream());
				outstr.writeObject("File Received");
			}
		}	        
		catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
