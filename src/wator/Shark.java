package wator;

import java.util.ArrayList;
import java.util.List;

import core.Agent;
import core.BusyCellException;
import core.Environnement;
import core.Mortal;

public class Shark extends Fish {

	private final static int SHARK_BIRTH_CYCLE = 10;
	private final static int SHARK_HUNGER_CYCLE = 3;

	private int hungerCycle;
	private int currentHungerCycle;
	protected List<Agent> snackNeigbhoring;
	
	public Shark(Environnement env) {
		super(env, SHARK_BIRTH_CYCLE);
		this.hungerCycle = SHARK_HUNGER_CYCLE;
		this.currentHungerCycle = 0;
		this.snackNeigbhoring = new ArrayList<Agent>();
	}

	public Shark(Environnement env, int posX, int posY) throws BusyCellException {
		super(env, posX, posY, SHARK_BIRTH_CYCLE);		
		this.snackNeigbhoring = new ArrayList<Agent>();
	}
	
	public void decide() {
		super.decide();

		// Does nothing if dead
		if(this.dead)
			return;

		// Clear neighboring
		this.snackNeigbhoring.clear();
		
		// Search for food
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				int nextX = this.env.getNextX(this.posX, i);
				int nextY = this.env.getNextY(this.posY, j);
				
				if(nextX == this.posX && nextY == this.posY)
					continue;
				
				Agent a = this.env.getAgent(nextX, nextY);
						
				if(a != null && a instanceof Tuna) { 
					this.snackNeigbhoring.add(a);
				}
			}
		}
		
		// Eats if possible and moves to food cell
		if(this.snackNeigbhoring.size() > 0) {			
			Agent food = this.snackNeigbhoring.get(Agent.r.nextInt(this.snackNeigbhoring.size()));
			
			this.eat(food);

			this.oldPosX = this.posX;
			this.oldPosY = this.posY;
			this.posX = food.getPosX();
			this.posY = food.getPosY();
			
			this.env.moveAgent(this);	
			
			return;
		}
		else {
			this.currentHungerCycle++;
		}
		
		// Dies if doesn't eat since hungerCycle turns
		if(this.currentHungerCycle >= this.hungerCycle) {
			this.die();
			return;
		}
		
		// Moves if can't eat and still alive
		if(emptyNeighboring.size() > 0) {
			this.oldPosX = this.posX;
			this.oldPosY = this.posY;
			
			int[] coords = this.emptyNeighboring.remove(Agent.r.nextInt(this.emptyNeighboring.size()));
			
			this.posX = coords[0];
			this.posY = coords[1];
			
			this.env.moveAgent(this);			
		}
		
		
	}
	
	protected void giveBirth(int x, int y) throws BusyCellException {
		new Shark(this.env, x, y);	
	}
	
	protected void eat(Agent a) {
		((Mortal)a).die();
		this.currentHungerCycle = 0;	
	}
	
}