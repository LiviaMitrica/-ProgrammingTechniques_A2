package Simulation;

import server.Scheduler;
import server.Time;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Iterator;

import Client.RandomClient;
import readFile.Read;
import readFile.Write;

public class SimulationManager implements Runnable, Time{

	private Write output = new Write();
	private Read info = new Read();
	private int[] data;
	private String file;

	private Scheduler scheduler;
	private RandomClient client;
	private List<RandomClient> clients;
	private List<RandomClient> processedClients;
	private AtomicInteger currentTime;

	public SimulationManager(String[] args) {
		data= info.read(args[0]);
		file= args[1];
		client = new RandomClient();
		clients = client.generateClients(data[0], data[3], data[4], data[5], data[6]);
		//scheduler = new Scheduler(data[1], data[2]);
		scheduler = new Scheduler(data[1], this);
		processedClients = new ArrayList<RandomClient>();
		currentTime = new AtomicInteger(0);
	}

	// run "queues" as long as the time does not exceeds the specified simulation interval
	// choose clients having arrival=current time and add the to the scheduler then remove them
	public void run() {
		Collections.sort(clients);
		currentTime.set(0);
		while(currentTime.get() <= data[2]) {
			output.writeFile("Time "+currentTime.get(),file);
			output.writeFile("Waiting clients: ",file);
			RandomClient currentClient;
			for (Iterator<RandomClient> c = clients.iterator(); c.hasNext(); ) {
				currentClient = (RandomClient) c.next();
				if (currentClient.getArrival() != currentTime.get() && !currentClient.isProcessed()) 
					output.writeFile(currentClient.toString(),file);
				else{
					scheduler.addClient(currentClient);
					c.remove();
					processedClients.add(currentClient);
				}		    
			}
			scheduler.printQueues(file);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			currentTime.incrementAndGet();
			output.writeFile("",file);
		}
		avgTime();
	}

	// compute avg waiting time for clients which have been processed
	private void avgTime() {
		float avgTime = 0;
		for(RandomClient c: processedClients) {
			//output.writeFile(c.toString()+" "+c.isProcessed(),file);
			if(c.isProcessed())
				avgTime += c.getService();
		}

		avgTime /= processedClients.size();
		output.writeFile("\nAverage waiting time: "+avgTime, file);
	}

	public int getCurrentTime() {
		return currentTime.get();
	}

	public static void main(String[] args) {
		if(args.length==2) {
			Read info = new Read();
			int[] data = info.read(args[0]);
			if(data[7]!=-1&&data[8]!=-1) {
				SimulationManager simulationManager = new SimulationManager(args);
				Thread t = new Thread ( simulationManager );
				t.start();
			}
		}
		else
			System.out.println("Not enough arguments provided or incorrect input!");
	}
}
