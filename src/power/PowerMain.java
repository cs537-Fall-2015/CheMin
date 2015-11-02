package power;

import java.io.IOException;
import generic.RoverThreadHandler;

public class PowerMain {

	public static void main(String[] args) {
		Power_Server ps=null;
		try {
			ps = new Power_Server(9013);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread serverPower=RoverThreadHandler.getRoverThreadHandler().getNewThread(ps);
		serverPower.start();
	}
}