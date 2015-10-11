package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dijkstra.Wall;

public abstract class Vue implements Observer {

	private JFrame frame;
	private String name;
	
	protected JPanel envPanel;
	protected int width, height, cellSize;
	
	public Vue(int width, int height, int cellSize, String name) {
		this.width = width;
		this.height = height;
		this.cellSize = cellSize;
		this.name = name;
	}
	
	public void init() {
		this.frame = new JFrame(this.name);
		this.frame.setResizable(false);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
		
		this.envPanel = new JPanel();
		this.envPanel.setPreferredSize(new Dimension(this.width * this.cellSize, this.height * this.cellSize));
		this.envPanel.setBackground(Color.white);
		
		this.frame.setContentPane(this.envPanel);
		this.frame.pack();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		SMA sma = (SMA) arg0;
		Image offScreen = this.envPanel.createImage(this.envPanel.getWidth(), this.envPanel.getHeight());
		Graphics g = offScreen.getGraphics();
		
		g.setColor(this.envPanel.getBackground());
		g.fillRect(0, 0, this.envPanel.getWidth(), this.envPanel.getHeight());
		
		for(Agent a : sma.getAgents()) {
			a.draw(g, this.cellSize);
		}
		
		g.dispose();
		
		this.envPanel.getGraphics().drawImage(offScreen, 0, 0, this.envPanel);
		
	}
	
	

}
