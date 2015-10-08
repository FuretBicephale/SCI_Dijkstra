package dijkstra;

import core.Agent;
import core.BusyCellException;
import core.Environnement;

public class Chaser extends Agent {
	
	public Chaser(Environnement env) throws BusyCellException {
		super(env);
	}
	
	public Chaser(Environnement env, int x, int y) throws BusyCellException {
		super(env, x, y);
	}
	
	public void decide() {

	}

}
