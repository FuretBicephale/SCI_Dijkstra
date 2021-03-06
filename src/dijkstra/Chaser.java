package dijkstra;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import core.Agent;
import core.BusyCellException;
import core.Environnement;
import core.Mortal;

public class Chaser extends Agent {
	
	private int[][] dijkstra;
	
	public Chaser(Environnement env) {
		super(env);
		dijkstra = DijkstraComputer.computeDijkstra(this.env, this);
	}
	
	public Chaser(Environnement env, int x, int y) throws BusyCellException {
		super(env, x, y);
		dijkstra = DijkstraComputer.computeDijkstra(this.env, this);
	}
	
	public void decide() {
	
		List<Attractor> attractors = new ArrayList<Attractor>();
		int[][][] dijkstras;
		List<Integer> xMins = new ArrayList<Integer>();
		List<Integer> yMins = new ArrayList<Integer>();
		int randomlyChosenIndex;
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
		
		// Si il n'y a plus d'attracteurs, on arrete la poursuite.
		if (attractors.isEmpty()) return;
		
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
				
				if((nextX == this.posX && nextY == this.posY) || 
						(this.env.getAgent(nextX, nextY) != null && !(this.env.getAgent(nextX, nextY) instanceof Attractor)))
					continue;
				
				if(this.env.getAgent(nextX, nextY) != null && this.env.getAgent(nextX, nextY) instanceof Attractor) {
					xMins.clear();
					yMins.clear();
					xMins.add(nextX);
					yMins.add(nextY);
					break;
				}
				
				for(int k = 0; k < attractors.size(); k++) {
					if(minDijkstra == -1 || (dijkstras[k][nextX][nextY] != -1 && minDijkstra >= dijkstras[k][nextX][nextY])) {
						if (minDijkstra != dijkstras[k][nextX][nextY]) {
							minDijkstra = dijkstras[k][nextX][nextY];
							xMins.clear();
							yMins.clear();
						}
						
						xMins.add(nextX);
						yMins.add(nextY);
					}
				}
				
			}
		}
		
		// L'agent est probablement entouré de murs ou la cible est innaccessible.
		if (minDijkstra == -1) return;
		
		randomlyChosenIndex = Agent.r.nextInt(xMins.size());
		this.oldPosX = this.posX;
		this.oldPosY = this.posY;
		this.posX = xMins.get(randomlyChosenIndex);
		this.posY = yMins.get(randomlyChosenIndex);
		
		Agent a = this.env.getAgent(this.posX, this.posY);
		if(a != null && a instanceof Attractor && !((Mortal) a).isDead()) {
			((Mortal)a).die();
			//new Attractor(env); // Quand y en a plus y en a encore!
		}
		
		this.env.moveAgent(this);
		
		dijkstra = DijkstraComputer.computeDijkstra(this.env, this);
		
	}
	
	public int[][] getDijkstra() {
		return this.dijkstra;
	}

	public void draw(Graphics g, int cellSize) {
		
		g.setColor(new Color(0, 210, 0));
		g.fillOval(this.posX * cellSize, this.posY * cellSize, cellSize, cellSize);
		
		int[][] dijkstra;
		Attractor attractor = null;
		for(int i = 0; i < this.env.getWidth(); i++) {
			for(int j = 0; j < this.env.getHeight(); j++) {
				Agent a = this.env.getAgent(i, j);
				if(a != null && a instanceof Attractor) {
					attractor = (Attractor) a;
				}
			}
		}
		if (attractor==null) return;
		dijkstra = DijkstraComputer.computeDijkstra(env, attractor);
		
		for (int i=0 ; i<env.getWidth() ; i++) {
			for (int j=0 ; j<env.getHeight() ; j++) {
				if(this.env.getAgent(i, j) != null)
					continue;
				if(dijkstra[i][j] == -1) {
					g.setColor(new Color(0, 0, 0));	
					System.out.println(i + " - " + j + " = " +  this.env.getAgent(i, j));
				} else {
					g.setColor(new Color(Math.max(255-10*dijkstra[i][j], 0), Math.max(255-10*dijkstra[i][j], 0), Math.max(255-10*dijkstra[i][j], 0)));					
				}
				g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
			}
		}
	}

}
