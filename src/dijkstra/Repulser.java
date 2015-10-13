package dijkstra;

import java.awt.Color;
import java.awt.Graphics;

import core.Agent;
import core.BusyCellException;
import core.Environnement;

public class Repulser extends Agent {
	
	public static final int REPULSER_RANGE = 5;
	
	public Repulser(Environnement env) {
		super(env);
	}

	public Repulser(Environnement env, int posX, int posY) throws BusyCellException {
		super(env, posX, posY);
	}

	public void decide() {
		moveRandomly();
	}

	public void draw(Graphics g, int cellSize) {
		g.setColor(Color.RED);
		g.fillOval(this.posX * cellSize, this.posY * cellSize, cellSize, cellSize);
	}

}
