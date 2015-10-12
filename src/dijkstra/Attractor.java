package dijkstra;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

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
		
		//moveRandomly();
		fleeThreat();
		
		dijkstra = DijkstraComputer.computeDijkstra(this.env, this);
	
	}
	
	public int[][] getDijkstra() {
		return this.dijkstra;
	}

	@Override
	public void draw(Graphics g, int cellSize) {
		g.setColor(Color.ORANGE);
		g.fillOval(this.posX * cellSize, this.posY * cellSize, cellSize, cellSize);
	}
	
	private void moveRandomly() {
		int nextX = this.env.getNextX(this.posX, Agent.r.nextInt(3) - 1);
		int nextY = this.env.getNextY(this.posY, Agent.r.nextInt(3) - 1);
				
		while(this.env.getAgent(nextX, nextY) != null && this.env.getAgent(nextX, nextY) != this) {
			nextX = this.env.getNextX(this.posX, Agent.r.nextInt(3) - 1);
			nextY = this.env.getNextY(this.posY, Agent.r.nextInt(3) - 1);
		}
		
		this.oldPosX = this.posX;
		this.oldPosY = this.posY;
		this.posX = nextX;
		this.posY = nextY;
		
		this.env.moveAgent(this);
	}

	private void fleeThreat() {
		List<Chaser> chasers = new ArrayList<Chaser>();
		int[][][] dijkstras;
		int cellSafeness=Integer.MAX_VALUE, maxSafeness;
		int safestPlaceX=0, safestPlaceY=0;
		
		// Get the chasers
		for(int i = 0; i < this.env.getWidth(); i++) {
			for(int j = 0; j < this.env.getHeight(); j++) {
				Agent a = this.env.getAgent(i, j);
				if(a != null && a instanceof Chaser) {
					chasers.add((Chaser)a);
				}
			}
		}
		
		if (chasers.isEmpty()) {
			this.moveRandomly();
			return;
		}
		
		dijkstras = new int[chasers.size()][][];
		for(int i = 0; i < chasers.size(); i++) {
			dijkstras[i] = chasers.get(i).getDijkstra();
		}
		
		// Move to the safest place
		maxSafeness = -1;
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {

				int nextX = this.env.getNextX(this.posX, i);
				int nextY = this.env.getNextY(this.posY, j);

				if(nextX == this.posX && nextY == this.posY)
					continue;

				cellSafeness=Integer.MAX_VALUE;
				for(int k = 0; k < chasers.size(); k++) {
					/* On prend la valeur la plus petite, celle qui 
					 * correspond a une menace la plus proche. */
					if (dijkstras[k][nextX][nextY] < cellSafeness)
						cellSafeness = dijkstras[k][nextX][nextY];
				}
				
				if (cellSafeness > maxSafeness) {
					maxSafeness = cellSafeness;
					safestPlaceX = nextX;
					safestPlaceY = nextY;
				}
			}
		}
		
		if (maxSafeness < 0) { // Pas de solution sure possible.
			return;
		}
		
		this.oldPosX = this.posX;
		this.oldPosY = this.posY;
		this.posX = safestPlaceX;
		this.posY = safestPlaceY;
		
		this.env.moveAgent(this);
	}
}
