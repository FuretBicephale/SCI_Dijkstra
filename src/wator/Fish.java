package wator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.Agent;
import core.BusyCellException;
import core.Environnement;
import core.Mortal;

public abstract class Fish extends Mortal {
	
	private int birthCycle;
	private int currentBirthCycle;
	
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

	public void decide() {

		// Does nothing if dead
		if(this.dead)
			return;
		
		// Clear neighboring
		this.emptyNeighboring.clear();
		
		// Look neighboring
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				int nextX = this.env.getNextX(this.posX, i);
				int nextY = this.env.getNextY(this.posY, j);
				
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
			int[] coords = this.emptyNeighboring.remove(Agent.r.nextInt(this.emptyNeighboring.size()));
			try {
				this.giveBirth(coords[0], coords[1]);
			} catch(BusyCellException e) {
				System.err.println("Error : Trying to give birth in a busy cell");
			}
		} else {
			currentBirthCycle++;			
		}
		
	}
	
	protected abstract void giveBirth(int x, int y) throws BusyCellException;
	
}