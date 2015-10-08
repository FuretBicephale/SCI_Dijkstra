package dijkstra;

import java.util.ArrayList;
import java.util.List;

import core.Agent;
import core.BusyCellException;
import core.Environnement;
import core.Mortal;

public class Chaser extends Agent {
	
	public Chaser(Environnement env) {
		super(env);
	}
	
	public Chaser(Environnement env, int x, int y) throws BusyCellException {
		super(env, x, y);
	}
	
	public void decide() {
	
		List<Attractor> attractors = new ArrayList<Attractor>();
		int[][][] dijkstras;
		int minX = -1;
		int minY = -1;
		int minDijkstra = -1;
		
		// Get the attractors
		for(int i = 0; i < this.env.getWidth(); i++) {
			for(int j = 0; j < this.env.getHeight(); j++) {
				Agent a = this.env.getAgent(i, j);
				if(a != null && a instanceof Attractor) {
					attractors.add((Attractor)a);
				}
			}
		}
		
		// Get dijkstra for each attractor
		dijkstras = new int[attractors.size()][][];
		for(int i = 0; i < attractors.size(); i++) {
			dijkstras[i] = attractors.get(i).getDijkstra();
		}
		
		// Move to the nearest attractor
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {

				int nextX = this.env.getNextX(this.posX, i);
				int nextY = this.env.getNextY(this.posY, j);
				
				if(nextX == this.posX && nextY == this.posY)
					continue;
				
				for(int k = 0; k < attractors.size(); k++) {
					if(minDijkstra == -1 || minDijkstra > dijkstras[k][nextX][nextY]) {
						minDijkstra = dijkstras[k][nextX][nextY];
						minX = nextX;
						minY = nextY;
					}
				}
				
			}
		}
				
		Agent a = this.env.getAgent(minX, minY);
		if(a != null && a instanceof Attractor)
			((Mortal)a).die();
		
		this.oldPosX = this.posX;
		this.oldPosY = this.posY;
		this.posX = minX;
		this.posY = minY;
		
		this.env.moveAgent(this);
		
	}

}
