package core;

public abstract class Mortal extends Agent {

	protected boolean dead;
	
	public Mortal(Environnement env) {
		super(env);
	}
	
	public Mortal(Environnement env, int x, int y) throws BusyCellException {
		super(env, x, y);
	}
	
	public void die() {
		this.dead = true;
		this.env.removeAgent(this);
	}
	
	public boolean isDead() {
		return this.dead;
	}

}
