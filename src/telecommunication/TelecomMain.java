package telecommunication;

import java.io.IOException;

import generic.RoverThreadHandler;

public class TelecomMain {

	public static void main(String[] args) {
		Telecom_Server ps=null;
		try {
			ps = new Telecom_Server(9002);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread serverPower=RoverThreadHandler.getRoverThreadHandler().getNewThread(ps);
		serverPower.start();
	}

}
