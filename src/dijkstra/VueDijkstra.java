package dijkstra;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Observable;

import core.Agent;
import core.SMA;
import core.Vue;

public class VueDijkstra extends Vue {

	public VueDijkstra(int width, int height, int cellSize, String name) {
		super(width, height, cellSize, name);
	}

	@Override
	public void update(Observable o, Object arg) {
		SMA sma = (SMA)o;
		Image offScreen = this.envPanel.createImage(this.envPanel.getWidth(), this.envPanel.getHeight());
		//Graphics g = this.envPanel.getGraphics();
		Graphics g = offScreen.getGraphics();
		
		g.setColor(this.envPanel.getBackground());
		g.fillRect(0, 0, this.envPanel.getWidth(), this.envPanel.getHeight());
		
		for(Agent a : sma.getAgents()) {
			if(a instanceof Wall) {
				drawWall((Wall)a, g);
			}
		}
		
		this.envPanel.getGraphics().drawImage(offScreen, 0, 0, this.envPanel);

	}
	
	private void drawWall(Wall w, Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(w.getPosX()*this.cellSize, w.getPosY()*this.cellSize, this.cellSize, this.cellSize);		
	}

}
