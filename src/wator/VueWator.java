package wator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;

import core.Agent;
import core.SMA;
import core.Vue;

public class VueWator extends Vue {
	
	private BufferedWriter bwStats;
	private String statsFileName = "values.csv";
	private int idTurn = 0;

	public VueWator(int width, int height, int cellSize, String name) {
		super(width, height, cellSize, name);
		try {
			FileWriter fw = new FileWriter(statsFileName, false);
			this.bwStats = new BufferedWriter(fw);
			this.bwStats.write("id_turn;nb_tunas;nb_sharks;ratio\n");
			this.bwStats.close();
		} catch (IOException e) {
			System.err.println("Erreur : Impossible de creer le fichier csv.");
		}
	}
	
	public void update(Observable arg0, Object arg1) {
		SMA sma = (SMA)arg0;
		Image offScreen = this.envPanel.createImage(this.envPanel.getWidth(), this.envPanel.getHeight());
		//Graphics g = this.envPanel.getGraphics();
		Graphics g = offScreen.getGraphics();
		
		g.setColor(this.envPanel.getBackground());
		g.fillRect(0, 0, this.envPanel.getWidth(), this.envPanel.getHeight());

		int nbTuna = 0;
		int nbShark = 0;
		
		for(Agent a : sma.getAgents()) {
			eraseFish((Fish) a, g);
		}
		
		for(Agent a : sma.getDeadAgents()) {
			eraseDeadFish((Fish) a, g);
		}
		sma.clearDeadAgents();
		
		for(Agent a : sma.getAgents()) {
			if(a instanceof Tuna) {
				drawTuna((Tuna)a, g);
				nbTuna++;
			} else if(a instanceof Shark) {
				drawShark((Shark)a, g);
				nbShark++;
			}
		}
		
		// Ecriture dans le fichier CSV.
		try {
			FileWriter fw = new FileWriter(statsFileName, true);
			this.bwStats = new BufferedWriter(fw);
			this.bwStats.write((this.idTurn++) + ";" + nbTuna + ";" + nbShark + "\n");
			this.bwStats.close();
		} catch (IOException e) {
			System.err.println("Erreur : Impossible d'ecrire dans le fichier csv.");
		}
		
		this.envPanel.getGraphics().drawImage(offScreen, 0, 0, this.envPanel);
	}
	
	private void drawTuna(Tuna t, Graphics g) {
		g.setColor(Color.GRAY);
		g.fillOval(t.getPosX()*this.cellSize, t.getPosY()*this.cellSize, this.cellSize, this.cellSize);		
	}
	
	private void drawShark(Shark s, Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval(s.getPosX()*this.cellSize, s.getPosY()*this.cellSize, this.cellSize, this.cellSize);		
		
	}
	
	private void eraseFish(Fish f, Graphics g) {
		g.setColor(this.envPanel.getBackground());
		g.fillOval(f.getOldPosX()*this.cellSize, f.getOldPosY()*this.cellSize, this.cellSize, this.cellSize);	
	}
	
	private void eraseDeadFish(Fish f, Graphics g) {
		g.setColor(this.envPanel.getBackground());
		g.fillOval(f.getOldPosX()*this.cellSize, f.getOldPosY()*this.cellSize, this.cellSize, this.cellSize);
		g.fillOval(f.getPosX()*this.cellSize, f.getPosY()*this.cellSize, this.cellSize, this.cellSize);	
	}

}
