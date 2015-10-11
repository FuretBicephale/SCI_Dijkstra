package dijkstra;

import java.awt.Color;
import java.awt.Graphics;

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
		
		moveRandomly();
		
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

}
