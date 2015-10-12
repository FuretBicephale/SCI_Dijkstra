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
		
		this.dijkstra = DijkstraComputer.computeDijkstra(this.env, this);
				
		// Take care of repulsers
		for(int i = 0; i < this.env.getWidth(); i++) {
			for(int j = 0; j < this.env.getHeight(); j++) {
				Agent a = this.env.getAgent(i, j);
				if(a != null && a instanceof Repulser) {
					for(int x = -3; x < 3; x++) {
						for(int y = -3; y < 3; y++) {
							int realX = this.env.getNextX(i, x);
							int realY = this.env.getNextY(j, y);
							this.dijkstra[realX][realY] = Math.max(this.dijkstra[realX][realY], 
									this.env.getHeight() + this.env.getWidth() - (Math.abs(x) + Math.abs(y)));
						}
					}
				}
			}
		}
	
	}
	
	public int[][] getDijkstra() {
		return this.dijkstra;
	}

	public void draw(Graphics g, int cellSize) {
		g.setColor(Color.ORANGE);
		g.fillOval(this.posX * cellSize, this.posY * cellSize, cellSize, cellSize);
	}

	private void fleeThreat() {
		List<Chaser> chasers = new ArrayList<Chaser>();
		int[][][] dijkstras;
		int cellSafeness=Integer.MAX_VALUE, maxSafeness;
		List<Integer> safestPlacesX = new ArrayList<Integer>();
		List<Integer> safestPlacesY = new ArrayList<Integer>();
		int randomlyChosenIndex;
		
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

				if((nextX == this.posX && nextY == this.posY) || 
						(this.env.getAgent(nextX, nextY) != null))
					continue;

				cellSafeness=Integer.MAX_VALUE;
				for(int k = 0; k < chasers.size(); k++) {
					/* On prend la valeur la plus petite, celle qui 
					 * correspond a une menace la plus proche. */
					if (dijkstras[k][nextX][nextY] < cellSafeness)
						cellSafeness = dijkstras[k][nextX][nextY];
				}
				
				if (cellSafeness >= maxSafeness) {
					if (cellSafeness != maxSafeness) {
						safestPlacesX.clear();
						safestPlacesY.clear();
						maxSafeness = cellSafeness;
					}
					
					safestPlacesX.add(nextX);
					safestPlacesY.add(nextY);
				}
			}
		}
		
		if (maxSafeness < 0) { // Pas de solution sure possible.
			return;
		}
		
		randomlyChosenIndex = Agent.r.nextInt(safestPlacesX.size());
		this.oldPosX = this.posX;
		this.oldPosY = this.posY;
		this.posX = safestPlacesX.get(randomlyChosenIndex);
		this.posY = safestPlacesY.get(randomlyChosenIndex);
		
		this.env.moveAgent(this);
	}
}
