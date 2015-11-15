package chemin_module;

public class Parser {
	
	private static boolean powerOn = false;
	
	public Parser() {
	}
	
	public void check_message(String message) {
		switch(message.toLowerCase()) {	
			// If message is chemin_on, set the CCU to True
			case "full process":
				System.out.println("CCU has send request to CHEMIN to turn on");
				System.out.println("CHEMIN is requesting power and Sending a json file with power Requirements to Power group");
		!!!!!!!!!!!!!!!!!!! SEND MESSAGE TO CHEMIN PROCESS TO ASK FOR LAUNCHING
				powerOn = true;
				break;
			//If message is power on, start the chemin process else print the required message 
			case "power on":
				boolean process = false;
				if(powerOn){
		!!!!!!!!!!!!!!!!!!!		 SEND MESSAGE TO POWER TO ASK FOR TURN ON
				} else{
					System.out.println(" CHEMIN dont have permission from parser ,Yet !");
				}
	
				break;
			//If process is true, 
			case "power off":
				System.out.println(" Deleting Thread ..");
		!!!!!!!!!!!!!!!!!!! SEND MESSAGE TO POWER TO ASK FOR TURN OFF
				break;
		}
	}
}
