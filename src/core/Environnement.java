package core;

import java.util.ArrayList;
import java.util.List;

public class Environnement {
	
	private Agent espace[][];
	private int width, height;
	private boolean toric;
	
	// Contains agents added or removed during the simulation
	private List<Agent> newAgents, deadAgents;
	
	public Environnement(int width, int height, boolean toric) {
		this.width = width;
		this.height = height;
		this.toric = toric;
	}
	
	// Create the 2D array of Agent
	public void init() {
		this.espace = new Agent[width][height];
		this.newAgents = new ArrayList<Agent>();
		this.deadAgents = new ArrayList<Agent>();
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public List<Agent> getNewAgents() {
		return this.newAgents;
	}
	
	public List<Agent> getDeadAgents() {
		return this.deadAgents;
	}
	
	public void clearNewAgents() {
		this.newAgents.clear();
	}
	
	public void clearDeadAgents() {
		this.deadAgents.clear();
	}
	
	// Return the Agent at [x, y] or null if it's empty
	public Agent getAgent(int x, int y) {
		return this.espace[x][y];
	}
	
	public boolean isToric() {
		return toric;
	}
	
	public void putAgent(Agent a) {
		this.newAgents.add(a);
		this.espace[a.getPosX()][a.getPosY()] = a;
	}
	
	public void removeAgent(Agent a) {
		if(this.espace[a.getPosX()][a.getPosY()] == a) {
			this.deadAgents.add(a);
			this.espace[a.getPosX()][a.getPosY()] = null;			
		}
	}
	
	// Remove the agent on [oldPosX, oldPosY] and put it on [posX, posY]
	public void moveAgent(Agent a)/* throws BusyCellException */ {
//		if (espace[a.getPosX()][a.getPosY()] != null)
//			throw new BusyCellException("nut nut");
		this.espace[a.getOldPosX()][a.getOldPosY()] = null;
		this.espace[a.getPosX()][a.getPosY()] = a;		
	}
	
}
