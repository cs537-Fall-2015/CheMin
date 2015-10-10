package usecase;

import generic.RoverClientRunnable;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UseCaseClient extends RoverClientRunnable{

	public UseCaseClient(int port, InetAddress host) throws UnknownHostException {
		super(port, host);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			ObjectOutputStream oos = null;
		    ObjectInputStream ois = null;
		    Thread.sleep(2000);
	        for(int i=0; i<5;i++){
	            //establish socket connection to server
	            //socket = new Socket(host.getHostName(), 9876);
	            //write to socket using ObjectOutputStream
	            oos = new ObjectOutputStream(getRoverSocket().getNewSocket().getOutputStream());
	            System.out.println("Client: Sending request to Socket Server");
	            if(i==4)oos.writeObject("exit");
	            else oos.writeObject("Test "+i);
	            //read the server response message
	            ois = new ObjectInputStream(getRoverSocket().getSocket().getInputStream());
	            String message = (String) ois.readObject();
	            System.out.println("Client: Message from Server - " + message.toUpperCase());
	            //close resources
	            ois.close();
	            oos.close();
	            Thread.sleep(1000);
	        }
	        closeAll();
		}	        
        catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception error) {
			System.out.println("Client: Error:" + error.getMessage());
		}		
	} 
}
