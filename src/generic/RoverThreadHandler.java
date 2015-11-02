package generic;

public class RoverThreadHandler {
	
	private static RoverThreadHandler roverThreadHandler;	
	
	private RoverThreadHandler(){
		
	}
	
	public static RoverThreadHandler getRoverThreadHandler(){
		
		if(roverThreadHandler == null){
			roverThreadHandler = new RoverThreadHandler();
		}
		
		return roverThreadHandler;		
	}
	
	public Thread getNewThread(Runnable runnable){		
		return RoverThreadFactory.getRoverThreadFactory().newThread(runnable);
	}
}
