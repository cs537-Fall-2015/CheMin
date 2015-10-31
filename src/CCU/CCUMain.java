package CCU;

import java.io.IOException;
import java.net.UnknownHostException;

import generic.RoverThreadHandler;

public class CCUMain {
		
	public static void main(String[] args) {
		int ccuport=9008;
		CCU_Server ccuserver=null;
		try {
			ccuserver = new CCU_Server(ccuport);
		} catch (IOException e) {
		  e.printStackTrace();
		}
		Thread serverccu=RoverThreadHandler.getRoverThreadHandler().getNewThread(ccuserver);
		serverccu.start();
	}
}