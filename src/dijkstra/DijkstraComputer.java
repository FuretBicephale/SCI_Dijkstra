package dijkstra;

import java.util.ArrayList;
import java.util.List;

import billes.Bille;

import core.Agent;
import core.Environnement;

public class DijkstraComputer {

	public static int[][] computeDijkstra(Environnement env, Agent target) {
		
		int[][] result = new int[env.getWidth()][env.getHeight()];
		
		for(int i = 0; i < env.getWidth(); i++) {
			for(int j = 0; j < env.getHeight(); j++) {
				result[i][j] = -1;
			}			
		}
		
		result[target.getPosX()][target.getPosY()] = 0;
		
		List<int[]> cellToCompute = new ArrayList<int[]>();
		int[] targetCell = {target.getPosX(), target.getPosY()};
		cellToCompute.add(targetCell);
		
		while(cellToCompute.size() >= 1) {

			int[] cell = cellToCompute.remove(0);

			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {
												
					int nextX = env.getNextX(cell[0], i);
					int nextY = env.getNextY(cell[1], j);
					
					if(result[nextX][nextY] != -1 || env.getAgent(nextX, nextY) != null)
						continue;

					result[nextX][nextY] = result[cell[0]][cell[1]] + 1;
					
					int[] coords = {nextX, nextY};
					cellToCompute.add(coords);
					
				}
			}		
			
						
		}
		
		return result;		
	}
	
	public static void main(String[] args) {
		
		Environnement env = new Environnement(50, 50, true);
		env.init();
		
		Agent a = new Bille(env);
		
		int[][] result = DijkstraComputer.computeDijkstra(env, a);

		for(int i = 0; i < env.getWidth(); i++) {
			for(int j = 0; j < env.getHeight(); j++) {
				System.out.print(result[j][i] + "\t");
			}
			System.out.println();
		}
		
	}
	

}
