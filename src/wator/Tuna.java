package wator;

import java.util.ArrayList;
import java.util.List;

import core.Agent;
import core.BusyCellException;
import core.Environnement;

public class Tuna extends Fish {

	private final static int TUNA_BIRTH_CYCLE = 5;
	protected List<int[]> predatorNeigbhoring;
	
	public Tuna(Environnement env) {
		super(env, TUNA_BIRTH_CYCLE);
		this.predatorNeigbhoring = new ArrayList<int[]>();
	}
	
	public Tuna(Environnement env, int posX, int posY) throws BusyCellException {
		super(env, posX, posY, TUNA_BIRTH_CYCLE);	
		this.predatorNeigbhoring = new ArrayList<int[]>();	
	}

	public void decide() {
		super.decide();

		// Does nothing if dead
		if(this.dead) {
			return;			
		}
				
		// Clear neighboring
		this.predatorNeigbhoring.clear();
		
		// Search predators
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				int nextX = this.getNextX(i);
				int nextY = this.getNextY(j);
				
				if(nextX == this.posX && nextY == this.posY)
					continue;
				
				Agent a = this.env.getAgent(nextX, nextY);
						
				if(a != null && a instanceof Shark) { 
					int[] coords = {nextX, nextY};
					this.predatorNeigbhoring.add(coords);
				}
			}
		}
		
		if(emptyNeighboring.size() > 0) {
			this.oldPosX = this.posX;
			this.oldPosY = this.posY;
			
			int[] coords = this.emptyNeighboring.remove(this.r.nextInt(this.emptyNeighboring.size()));
			this.posX = coords[0];
			this.posY = coords[1];
			
			this.env.moveAgent(this);			
		}
		
	}
	
	protected void giveBirth(int x, int y) throws BusyCellException {
		Tuna child = new Tuna(this.env, x, y);		
	}

}