package dijkstra;

import java.awt.Color;
import java.awt.Graphics;

import core.Agent;
import core.BusyCellException;
import core.Environnement;

public class Wall extends Agent {

	public Wall(Environnement env) {
		super(env);
	}

	public Wall(Environnement env, int posX, int posY) throws BusyCellException {
		super(env, posX, posY);
	}

	@Override
	public void decide() {

	}

	@Override
	public void draw(Graphics g, int cellSize) {
		g.setColor(new Color(128, 64, 64));
		g.fillRect(this.posX * cellSize, this.posY * cellSize, cellSize, cellSize);
	}

}
