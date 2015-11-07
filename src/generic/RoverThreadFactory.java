package generic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;


class RoverThreadFactory implements ThreadFactory {

	private int          counter;
    private String       name;
    private List<String> stats;
    private static RoverThreadFactory roverThreadFactory;
    
    public static RoverThreadFactory getRoverThreadFactory(){
    	if(roverThreadFactory == null){
    		roverThreadFactory = new RoverThreadFactory("");
    	}
    	
    	return roverThreadFactory;
    }
    
    private RoverThreadFactory(String name)
    {
       counter = 1;
       this.name = name;
       stats = new ArrayList<String>();
    }

	@Override
	public Thread newThread(Runnable runnable) {
		Thread t = new RoverThread(runnable, name + "-Thread_" + counter);
	      counter++;
	      stats.add(String.format("Created thread %d with name %s on %s \n", t.getId(), t.getName(), new Date()));
	      return t;
	}
	
	public String getStats(){
      StringBuffer buffer = new StringBuffer();
      Iterator<String> it = stats.iterator();
      while (it.hasNext())
      {
         buffer.append(it.next());
      }
      return buffer.toString();
   }
	   
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStats(List<String> stats) {
		this.stats = stats;
	}
}
