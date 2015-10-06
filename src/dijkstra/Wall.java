package dijkstra;

import core.Agent;
import core.BusyCellException;
import core.Environnement;

public class Wall extends Agent {

	public Wall(Environnement env) {
		super(env);
	}

	public Wall(Environnement env, int posX, int posY) throws BusyCellException {
		super(env, posX, posY);
	}

	@Override
	public void decide() {

	}

}
