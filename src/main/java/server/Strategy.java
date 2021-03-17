package server;

import java.util.List;

import Client.RandomClient;

public interface Strategy {
	public void addNewClient( List<Queue> queue, RandomClient client);
}
