package dijkstra;

import core.BusyCellException;
import core.Environnement;
import core.Mortal;

public class Attractor extends Mortal {
	
	public Attractor(Environnement env) throws BusyCellException {
		super(env);
	}
	
	public Attractor(Environnement env, int x, int y) throws BusyCellException {
		super(env, x, y);
	}

	public void decide() {

	}

}
