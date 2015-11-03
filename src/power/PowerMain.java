package power;

import java.io.IOException;
import generic.RoverThreadHandler;

public class PowerMain {

	public static void main(String[] args) {
		Power ps=null;
		try {
			ps = new Power(9013);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread serverPower=RoverThreadHandler.getRoverThreadHandler().getNewThread(ps);
		serverPower.start();
	}
}