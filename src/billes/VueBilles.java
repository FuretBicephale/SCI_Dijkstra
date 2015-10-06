package billes;

import java.awt.Graphics;
import java.util.Observable;

import core.Agent;
import core.SMA;
import core.Vue;

public class VueBilles extends Vue {

	public VueBilles(int width, int height, int marbleSize) {
		super(width, height, marbleSize, "SCI_Billes");
	}
	
	public void update(Observable arg0, Object arg1) {
		SMA sma = (SMA)arg0;
		Graphics g = this.envPanel.getGraphics();

		for(Agent a : sma.getAgents()) {
			eraseMarble((Bille)a, g);
			drawMarble((Bille)a, g);
		}
		
	}
	
	private void drawMarble(Bille a, Graphics g) {
		g.setColor(a.getColor());
		g.fillOval(a.getPosX()*this.cellSize, a.getPosY()*this.cellSize, this.cellSize, this.cellSize);
	}
	
	private void eraseMarble(Bille a, Graphics g) {
		g.setColor(this.envPanel.getBackground());
		g.fillOval(a.getOldPosX()*this.cellSize, a.getOldPosY()*this.cellSize, this.cellSize, this.cellSize);
	}

}
