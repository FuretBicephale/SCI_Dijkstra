package dijkstra;

import core.SMA;

public class Simulation {
		
	public static void printHelp() {
		System.err.println("Use : Java Simulation envWidth envHeight cellSize toric sleepLength nbAttractors nbChasers nbrRepulsers nbWalls nbTurns");
		System.err.println("envWidth = Environment width (int)");
		System.err.println("envHeight = Environment height (int)");
		System.err.println("cellSize = Graphical size of each cell (int)");
		System.err.println("toric = Defines if the environment is toric (boolean)");
		System.err.println("sleepLength = Time in ms to wait between each turn (int)");
		System.err.println("nbAttractors = Number of attractors created for the simulation (int)");
		System.err.println("nbChasers = Number of chasers created for the simulation (int)");
		System.err.println("nbRepulser = Number of repulsers created for the simulation (int)");
		System.err.println("nbWalls = Number of walls created for the simulation (int)");
		System.err.println("nbTurns = Number of turns of the simulation (int)");
	}
	
	// Use : Java Simulation envWidth envHeight cellSize toric sleepLength nbAttractors nbChasers nbRepulsers nbWalls nbTurns
	public static void main(String[] args) {
		
		int width, height, cellSize, sleepLength, nbAttractors, nbChasers, nbRepulsers, nbWalls, nbTurns=0;
		boolean toric, sansFin = false;
		
		if(args.length < 9 || args.length > 10) {
			System.err.println("Error : Unexpected number of parameters");
			printHelp();
			return;
		}
		
		try {
			width = Integer.parseInt(args[0]);
			height = Integer.parseInt(args[1]);
			cellSize = Integer.parseInt(args[2]);
			toric = Boolean.parseBoolean(args[3]);
			sleepLength = Integer.parseInt(args[4]);
			nbAttractors = Integer.parseInt(args[5]);
			nbChasers = Integer.parseInt(args[6]);
			nbRepulsers = Integer.parseInt(args[7]);
			nbWalls = Integer.parseInt(args[8]);
			if (args.length == 10)
				nbTurns = Integer.parseInt(args[9]);
			else
				sansFin = true;
		} catch(NumberFormatException e) {
			System.err.println("Error : Incorrect parameters");
			printHelp();
			return;
		}

		VueDijkstra vue = new VueDijkstra(width, height, cellSize, "SCI_Dijkstra");
		vue.init();
		
		SMA sma = new SMA(width, height, toric, sleepLength);
		sma.init();
		sma.addObserver(vue);
		
		for(int i = 0; i < nbWalls; i++) {
			new Wall(sma.getEnv());
		}
		
		for(int i = 0; i < nbAttractors; i++) {
			new Attractor(sma.getEnv());
		}
		
		for(int i = 0; i < nbChasers; i++) {
			new Chaser(sma.getEnv());
		}
		
		for(int i = 0; i < nbRepulsers; i++) {
			new Repulser(sma.getEnv());
		}
				
		try {
			if (sansFin)
				sma.run();
			else
				sma.run(nbTurns);
		} catch (InterruptedException e) {
			System.err.println("Error : Unexpected interruption during simulation");
		}
		
	}

}
