package billes;

import core.SMA;

public class Simulation {
	
	public static void printHelp() {
		System.err.println("Use : Java Simulation envWidth envHeight marbleSize toric sleepLength nbMarbles nbTurns");
		System.err.println("envWidth = Environment width (int)");
		System.err.println("envHeight = Environment height (int)");
		System.err.println("marbleSize = Graphical size of each marble (int)");
		System.err.println("toric = Defines if the environment is toric (boolean)");
		System.err.println("sleepLength = Time in ms to wait between each turn (int)");
		System.err.println("nbMarbles = Number of marbles created for the simulation (int)");
		System.err.println("nbTurns = Number of turns of the simulation (int)");
	}
	
	// Use : Java Simulation envWidth envHeight marbleSize toric sleepLength nbMarbles nbTurns
	public static void main(String[] args) {
		
		int width, height, marbleSize, sleepLength, nbMarbles, nbTurns=0;
		boolean toric, sansFin = false;
		
		if(args.length < 6 || args.length > 7) {
			System.err.println("Error : Unexpected number of parameters");
			printHelp();
			return;
		}
		
		try {
			width = Integer.parseInt(args[0]);
			height = Integer.parseInt(args[1]);
			marbleSize = Integer.parseInt(args[2]);
			toric = Boolean.parseBoolean(args[3]);
			sleepLength = Integer.parseInt(args[4]);
			nbMarbles = Integer.parseInt(args[5]);
			if (args.length == 7)
				nbTurns = Integer.parseInt(args[6]);
			else
				sansFin = true;
		} catch(NumberFormatException e) {
			System.err.println("Error : Incorrect parameters");
			printHelp();
			return;
		}

		VueBilles vue = new VueBilles(width, height, marbleSize, "SCI_Billes");
		vue.init();
		
		SMA sma = new SMA(width, height, toric, sleepLength);
		sma.init();
		sma.addObserver(vue);
		
		for(int i = 0; i < nbMarbles; i++) {
			new Bille(sma.getEnv());
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
