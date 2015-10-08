package wator;

import core.SMA;

public class Simulation {
	
	public static void printHelp() {
		System.err.println("Use : Java Simulation envWidth envHeight cellSize toric sleepLength nbTunas nbSharks nbTurns");
		System.err.println("envWidth = Environment width (int)");
		System.err.println("envHeight = Environment height (int)");
		System.err.println("cellSize = Graphical size of each cell (int)");
		System.err.println("toric = Defines if the environment is toric (boolean)");
		System.err.println("sleepLength = Time in ms to wait between each turn (int)");
		System.err.println("nbTunas = Number of tunas created for the simulation (int)");
		System.err.println("nbSharks = Number of sharks created for the simulation (int)");
		System.err.println("nbTurns = Number of turns of the simulation (int)");
	}
	
	// Use : Java Simulation envWidth envHeight cellSize toric sleepLength nbTunas nbSharks nbTurns
	public static void main(String[] args) {
		
		int width, height, cellSize, sleepLength, nbTunas, nbSharks, nbTurns=0;
		boolean toric, sansFin = false;
		
		if(args.length < 7 || args.length > 8) {
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
			nbTunas = Integer.parseInt(args[5]);
			nbSharks = Integer.parseInt(args[6]);
			if (args.length == 8)
				nbTurns = Integer.parseInt(args[7]);
			else
				sansFin = true;
		} catch(NumberFormatException e) {
			System.err.println("Error : Incorrect parameters");
			printHelp();
			return;
		}

		VueWator vue = new VueWator(width, height, cellSize, "SCI_Wator");
		vue.init();
		
		SMA sma = new SMA(width, height, toric, sleepLength);
		sma.init();
		sma.addObserver(vue);
		
		for(int i = 0; i < nbTunas; i++) {
			new Tuna(sma.getEnv());
		}
		
		for(int i = 0; i < nbSharks; i++) {
			new Shark(sma.getEnv());
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
