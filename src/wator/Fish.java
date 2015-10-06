package wator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.Agent;
import core.BusyCellException;
import core.Environnement;

public abstract class Fish extends Agent {
	
	private int birthCycle;
	private int currentBirthCycle;
	
	protected boolean dead;
	protected List<int[]> emptyNeighboring;

	public Fish(Environnement env, int birthCycle) {
		super(env);
		this.birthCycle = birthCycle;
		this.currentBirthCycle = 0;
		this.emptyNeighboring = new ArrayList<int[]>();
	}
	
	public Fish(Environnement env, int posX, int posY, int birthCycle) throws BusyCellException {
		super(env, posX, posY);
		this.birthCycle = birthCycle;
		this.currentBirthCycle = 0;
		this.emptyNeighboring = new ArrayList<int[]>();
	}

	protected int getNextX(int step) {		
		int newPosX;
		
		if(this.env.isToric()) {
			newPosX = (this.env.getWidth() + this.posX + step) % this.env.getWidth();
		} else {
			if(this.posX + step >= this.env.getWidth() || this.posX + step < 0)
				step = 0;
			newPosX = this.posX + step;
		}
		
		return newPosX;		
	}
	
	protected int getNextY(int step) {		
		int newPosY;
		
		if(this.env.isToric()) {
			newPosY = (this.env.getHeight() + this.posY + step) % this.env.getHeight();
		} else {
			if(this.posY + step >= this.env.getHeight() || this.posY + step < 0)
				step = 0;
			newPosY = this.posY + step;
		}
		
		return newPosY;
	}

	public void decide() {

		// Does nothing if dead
		if(this.dead)
			return;
		
		// Clear neighboring
		this.emptyNeighboring.clear();
		
		// Look neighboring
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				int nextX = this.getNextX(i);
				int nextY = this.getNextY(j);
				
				if(nextX == this.posX && nextY == this.posY)
					continue;
				
				if(this.env.getAgent(nextX, nextY) == null) { 
					int[] coords = {nextX, nextY};
					this.emptyNeighboring.add(coords);
				}
			}
		}		
		
		// Suppression des doublons
		for (int i=0 ; i<this.emptyNeighboring.size()-1 ; i++) {
			int[] coords = this.emptyNeighboring.get(i);
			for (int j=i+1 ; j<this.emptyNeighboring.size() ;) {
				if (Arrays.equals(coords, this.emptyNeighboring.get(j)))
					this.emptyNeighboring.remove(j);
				else
					j++;
			}
		}
		
		// Gives birth if possible
		if(this.currentBirthCycle >= this.birthCycle && this.emptyNeighboring.size() > 0) {
			currentBirthCycle = 0;
			int[] coords = this.emptyNeighboring.remove(this.r.nextInt(this.emptyNeighboring.size()));
			try {
				this.giveBirth(coords[0], coords[1]);
			} catch(BusyCellException e) {
				System.err.println("Error : Trying to give birth in a busy cell");
			}
		} else {
			currentBirthCycle++;			
		}
		
	}
	
	protected void die() {
		this.dead = true;
		this.env.removeAgent(this);
	}
	
	protected abstract void giveBirth(int x, int y) throws BusyCellException;
	
}