package telecommunication;

import java.io.IOException;

import generic.RoverThreadHandler;

public class TelecomMain {

	public static void main(String[] args) {
		Telecom ps=null;
		try {
			ps = new Telecom(9002);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread serverPower=RoverThreadHandler.getRoverThreadHandler().getNewThread(ps);
		serverPower.start();
	}

}
