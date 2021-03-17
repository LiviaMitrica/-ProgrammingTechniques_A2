package server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import Client.RandomClient;

public class Queue implements Runnable {

	private String name;
	private BlockingQueue<RandomClient> clients;
	private AtomicInteger waitingPeriod;
	private Time currentTime;
	private RandomClient currentClient;

	public Queue(String name, Time currentTime) {
		this.name = name;
		this.currentTime = currentTime;
		this.waitingPeriod = new AtomicInteger(0);
		clients = new LinkedBlockingQueue<>();
		currentClient = null;
	}

	//add client in the queue and increase waiting period
	public void addClient( RandomClient client) {
		try {
			clients.put(client);
			waitingPeriod.addAndGet(client.getService());
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void run() {

		while (true) {
			try {
				currentClient = clients.take();// retrieve and remove first client in the queue
				Thread.sleep(currentClient.getService()*1000);// thread sleeps for the time of processing, multiply with 1000 to get time in sec	
				currentClient.setFinishTime( currentTime.getCurrentTime() );
				waitingPeriod.addAndGet(-currentClient.getService());
				currentClient.setProcessed(true);
				currentClient = null;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String toString() {
		return name ;
	}

	public int getWaitingPeriod() {
		return waitingPeriod.get();
	}

	public RandomClient getCurrentClient() {
		return currentClient;
	}

	public BlockingQueue<RandomClient> getClients() {
		return clients;
	}	
	
}
