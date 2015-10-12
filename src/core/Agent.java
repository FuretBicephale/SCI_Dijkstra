package core;

import java.awt.Graphics;
import java.util.Random;

public abstract class Agent {
	
	protected static Random r = new Random();
	
	protected int posX;
	protected int posY;
	protected int oldPosX;
	protected int oldPosY;
	protected Environnement env;
	
	// Create the agent and choose a random position (cell must be empty) in the environment
	public Agent(Environnement env) {
		
		boolean positionFound = false;
		this.env = env;
		
		while (!positionFound) {
			int x = r.nextInt(env.getWidth());
			int y = r.nextInt(env.getHeight());
			
			if (env.getAgent(x, y) == null) {
				this.oldPosX = this.posX = x;
				this.oldPosY = this.posY = y;
				this.env.putAgent(this);
				positionFound = true;
			}
		}		
	}
	
	// Create the agent at [x, y] (cell must be empty) in the environment
	public Agent(Environnement env, int posX, int posY) throws BusyCellException {
				
		if(env.getAgent(posX, posY) == null) {
			this.env = env;
			this.oldPosX = this.posX = posX;
			this.oldPosY = this.posY = posY;
			this.env.putAgent(this);
		} else {
			throw new BusyCellException("Cell [" + posX + ", " + posY + "] already occupied");
		}
		
	}

	public int getOldPosX() {
		return this.oldPosX;
	}

	public int getOldPosY() {
		return this.oldPosY;
	}
	
	public int getPosX() {
		return this.posX;
	}
	
	public int getPosY() {
		return this.posY;
	}
	
	protected void moveRandomly() {
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
		
	// Ask agent to make a decision for the current turn
	public abstract void decide();
	
	public abstract void draw(Graphics g, int cellSize);
	
}

