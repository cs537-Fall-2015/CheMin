package chemin_module;

import java.io.IOException;
import java.io.ObjectInputStream;

import generic.RoverServerRunnable;

public class CheminServer extends RoverServerRunnable {
	
	Parser chemin_parser = null;
	
	public CheminServer(int port) throws IOException {
		super(port);
		chemin_parser = new Parser(9008,null); //port can be modified
	}

	@Override
	public void run() {
		try {
			while (true) {

				System.out.println("CHEMIN Server -> Waiting for Request");

				// creating socket and waiting for client connection or for CCU
				getRoverServerSocket().openSocket();

				// read from socket to ObjectInputStream object
				ObjectInputStream inputFromAnotherObject = new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());

				// convert ObjectInputStream object to String
				String message=inputFromAnotherObject.readObject().toString();
				
				//wait for 7 sec
				Thread.sleep(7000);
				
				//send message to parser
				switch(chemin_parser.check_message(message)){
					case 1:
						System.out.println("Chemin server: internal message received");
						break;
					case 2:
						System.out.println("Chemin server: external message received");
						break;
					default:
						System.out.println("Chemin server: wrong/unknown command");
				}
				// close resources
				inputFromAnotherObject.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception error) {
			System.out.println("Server: Error: " + error.getMessage());
		}
	}
}
