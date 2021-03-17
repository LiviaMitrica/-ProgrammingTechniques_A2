package server;

import java.util.ArrayList;
import java.util.List;

import Client.RandomClient;
import readFile.Write;

public class Scheduler  {
	
	private Write output = new Write();
	private List<Queue> queue;
	private int maxNbQueues;
	private Time simInterval;
	private Strategy strategy;
	private List<Thread> threads = new ArrayList<Thread>();
	
	public Scheduler(int maxNbQueues, Time simInterval) {
		this.maxNbQueues = maxNbQueues;
		this.simInterval = simInterval ;
		queue = new ArrayList<>();	
		dispatchTime();
	}
	
	// add client to queue where it is the least waiting time
	public void addClient(RandomClient client) {	
		strategy = new ShortestTime();
		strategy.addNewClient ( getQueue(), client);
	}
	
	// create and start running the threads of corresponding queues
	private void dispatchTime() {
		for(int i=1; i<=maxNbQueues; i++)
			queue.add(new Queue("Queue "+i, simInterval));
		for( Queue q: queue) {
			Thread th =new Thread(q);
			th.start();
			threads.add(th);
		}
			
	}

	public List<Queue> getQueue() {
		return queue;
	}	

	public void printQueues(String file) {
		// print the content of each queue at each moment
		int count = 0; // current index of queue
		for( Thread t: threads ) {			
			//output.writeFile("Queue "+ count + " :" + t.getState(),file);
			if( t.getState().toString().equals("TIMED_WAITING")||t.getState().toString().equals("RUNNABLE")) {	
				String queueS = "Queue "+ (count+1) + " :" + " " + queue.get(count).getCurrentClient().toString();
				//output.writeFile("Queue "+ (count+1) + " - "+ queue.get(count).getClients().size() + " :" + " " + queue.get(count).getCurrentClient().toString(),file);
				for(RandomClient c: queue.get(count).getClients())
					queueS += " " + c.toString();	
				output.writeFile(queueS, file);
			}
				
			else
				output.writeFile("Queue "+ (count+1) + " :" + " " + "closed",file);
			count++;
		}
			
	}
}
