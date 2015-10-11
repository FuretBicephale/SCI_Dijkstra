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

		int nbTuna = 0;
		int nbShark = 0;

		sma.clearDeadAgents();
		
		for(Agent a : sma.getAgents()) {
			if(a instanceof Tuna) {
				nbTuna++;
			} else if(a instanceof Shark) {
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
	}

}
