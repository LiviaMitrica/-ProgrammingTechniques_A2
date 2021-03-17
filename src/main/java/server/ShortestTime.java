package server;

import java.util.List;

import Client.RandomClient;

public class ShortestTime implements Strategy {

	private int minInd = 0;
	
	@Override
	public void addNewClient(List<Queue> queue, RandomClient client) {
		int min = queue.get(0).getWaitingPeriod();

        for (int i = 0; i < queue.size(); ++i)
            if (min > queue.get(i).getWaitingPeriod()) {
                min = queue.get(i).getWaitingPeriod();
                minInd = i;
            }
        queue.get(minInd).addClient(client);
		
	}
}
