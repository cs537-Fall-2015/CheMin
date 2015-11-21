package chemin_module;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import generic.RoverServerRunnable;

public class CheminServer extends RoverServerRunnable {
	
	Parser chemin_parser = null;
	CheminProcess process = null;
	
	public CheminServer(int port,CheminProcess p) throws IOException {
		super(port);
		process = p;
		System.out.println("new process as a part of server" + p);
		//chemin_parser = new Parser(9008,null,p); //port can be modified
	}

	@Override
	public void run() {
		try {
			while (true) {

				System.out.println("CHEMIN Server -> Waiting for Request");

				// creating socket and waiting for client connection or for CCU
				System.out.println("chemin server is listening to" + getRoverServerSocket().getPort());
				getRoverServerSocket().openSocket();
				getRoverServerSocket().openSocket();
				// read from socket to ObjectInputStream object
				ObjectInputStream inputFromAnotherObject = new ObjectInputStream(getRoverServerSocket().getSocket().getInputStream());
				
				// convert ObjectInputStream object to String
				String message=inputFromAnotherObject.readObject().toString();
				System.out.println("server listened to message" + message);
				
				//wait for 7 sec
				//Thread.sleep(7000);
				if(message.equals("reset")) {
					
					System.out.println("resuming chemin+process");
					process.isExit = false;
				}
				if(message.equals("power_off")) {
					
					process.isExit = true;
					@SuppressWarnings("resource")
					Socket socket = new Socket("localhost",9013);
					ObjectOutputStream outstr = null;
					try {
						outstr = new ObjectOutputStream(socket.getOutputStream());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						outstr.writeObject("turning power off");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					System.out.println("turning off power");
//					PrintWriter  writer = new PrintWriter("./src/resources/command_file.txt", "UTF-8");
//					writer.write("");
//					writer.close();
//					process = null;
//					for (Thread t : Thread.getAllStackTraces().keySet()) 
//					{  if (t.getState()==Thread.State.RUNNABLE) {
//						
//						System.out.println("abc");
//						t.interrupt(); 
//						
//					}
//					     
//					} 
					//System.exit(0);
				
				}
				
				
				//send message to parser
//				switch(chemin_parser.check_message(message)){
//					case 1:
//						System.out.println("Chemin server: internal message received");
//						break;
//					case 2:
//						System.out.println("Chemin server: external message received");
//						break;
//					default:
//						System.out.println("Chemin server: wrong/unknown command");
//				}
//				// close resources
//				//inputFromAnotherObject.close();
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
