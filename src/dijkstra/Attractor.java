package dijkstra;

import core.Agent;
import core.BusyCellException;
import core.Environnement;
import core.Mortal;

public class Attractor extends Mortal {
	
	private int[][] dijkstra;
	
	public Attractor(Environnement env) {
		super(env);
		dijkstra = DijkstraComputer.computeDijkstra(this.env, this);
	}
	
	public Attractor(Environnement env, int x, int y) throws BusyCellException {
		super(env, x, y);
		dijkstra = DijkstraComputer.computeDijkstra(this.env, this);
	}

	public void decide() {
		
		int nextX = this.env.getNextX(this.posX, Agent.r.nextInt() * 2 - 1);
		int nextY = this.env.getNextY(this.posY, Agent.r.nextInt() * 2 - 1);
				
		while(nextX == this.posX && nextY == this.posY)
			nextX = this.env.getNextX(this.posX, Agent.r.nextInt() * 2 - 1);
			nextY = this.env.getNextY(this.posY, Agent.r.nextInt() * 2 - 1);
		
		this.oldPosX = this.posX;
		this.oldPosY = this.posY;
		this.posX = nextX;
		this.posY = nextY;
		
		this.env.moveAgent(this);
		dijkstra = DijkstraComputer.computeDijkstra(this.env, this);
	
	}
	
	public int[][] getDijkstra() {
		return this.dijkstra;
	}

}
