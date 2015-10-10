package usecase;

import generic.RoverServerRunnable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UseCaseServer extends RoverServerRunnable{

	public UseCaseServer(int port) throws IOException {
		super(port);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {			
			while(true){				
	            System.out.println("Server: Waiting for client request");	            
				//creating socket and waiting for client connection
	            getRoverServerSocket().openSocket();
	            //read from socket to ObjectInputStream object
	            ObjectInputStream ois = new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
	            //convert ObjectInputStream object to String
	            String message = (String) ois.readObject();
	            System.out.println("Server: Message Received from Client - " + message.toUpperCase());
	            //create ObjectOutputStream object
	            ObjectOutputStream oos = new ObjectOutputStream(getRoverServerSocket().getSocket().getOutputStream());
	            //write object to Socket
	            oos.writeObject("Server says Hi Client - "+message);
	            //close resources
	            ois.close();
	            oos.close();
	            //getRoverServerSocket().closeSocket();
	            //terminate the server if client sends exit request
	            if(message.equalsIgnoreCase("exit")) break;
	        }
			System.out.println("Server: Shutting down Socket server!!");
	        //close the ServerSocket object
	        closeAll();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    }
        catch(Exception error){
        	System.out.println("Server: Error:" + error.getMessage());
        }
		
	}
}
