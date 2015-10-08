package core;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

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

}
