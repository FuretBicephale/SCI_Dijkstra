package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public class SMA extends Observable {
	
	private int sleepLength;
	private Environnement env;
	private List<Agent> agents, deadAgents;
	
	public SMA(int width, int height, boolean toric, int sleepLength) {
		this.env = new Environnement(width, height, toric);
		this.sleepLength = sleepLength;
	}
	
	// Initialize environment and agent
	public void init() {
		this.env.init();
		this.agents = new ArrayList<Agent>();	
		this.deadAgents = new ArrayList<Agent>();		
	}
	
	public Environnement getEnv() {
		return this.env;
	}

	public int getNbAgents() {
		return this.agents.size();
	}

	public List<Agent> getAgents() {
		return this.agents;
	}
	
	public List<Agent> getDeadAgents() {
		return this.deadAgents;
	}
	
	public void clearDeadAgents() {
		this.deadAgents.clear();
	}

	public void addAgent(Agent a) {
		this.agents.add(a);
	}
	
	public void removeAgent(Agent a) {
		this.agents.remove(a);
	}
	
	// Run the simulation for nbTours turns. Each turn, agents is shuffled and each agent are asked to make a decision.
	public void run(int nbTours) throws InterruptedException {
//		this.setChanged();
//		this.notifyObservers();
		
		for(int i = 0; i < nbTours; i++) {
			this.doTurn();
			Thread.sleep(sleepLength);	
		}
		
	}
	
	// Run the simulation indefinitely.
	public void run() throws InterruptedException {
//		this.setChanged();
//		this.notifyObservers();
		
		while(true) {
			this.doTurn();
			Thread.sleep(sleepLength);			
		}
	}
	
	public void doTurn() {
		
		Collections.shuffle(this.agents);
				
		for(Agent a : this.agents) {
			a.decide();
		}
		
		for(Agent a : this.env.getNewAgents()) {
			this.addAgent(a);
		}
		this.env.clearNewAgents();

		for(Agent a : this.env.getDeadAgents()) {
			this.removeAgent(a);
			this.deadAgents.add(a);
		}
		this.env.clearDeadAgents();
		
		this.setChanged();
		this.notifyObservers();

	}
}
