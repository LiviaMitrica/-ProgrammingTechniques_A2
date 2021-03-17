package Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomClient implements Comparable<RandomClient>  {

	private int ID;
	private int arrival;
	private int service;
	private int finishTime;
	private boolean processed = false;
	

	public RandomClient(int id, int arrival, int service) {
		this.ID = id;
		this.arrival = arrival;
		this.service = service;
	}

	public RandomClient() {
		
	}
	
 	public int getID() {
		return ID;
	}

	public int getArrival() {
		return arrival;
	}

	public int getService() {
		return service;
	}

	public void setService(int service) {
		this.service = service;
	}
	
	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}
	
	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public List<RandomClient> generateClients(int numberClients, int minArrival, int maxArrival, int minService, int maxService){
		List<RandomClient> clients = new ArrayList<RandomClient>();
		for( int i=1; i<=numberClients; i++ ) {
			Random rand = new Random();
			int id = i;
			int arrivalTime = rand.nextInt(maxArrival-minArrival+1)+minArrival;
			int serviceTime = rand.nextInt(maxService-minService+1)+minService;
			RandomClient client = new RandomClient(id, arrivalTime, serviceTime);
			clients.add(client);
		}
		return clients;
	}


	@Override
	public String toString() {
		return "(" + ID + ", " + arrival + ", " + service + ")";
	}

	public int compareTo(RandomClient o) {
		return this.arrival-o.arrival;
	}
	
}
