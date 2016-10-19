package controler;

import java.awt.Color;
import java.util.List;

import model.Creature;
import model.Grid;

public class WorldControler {
	private Grid grid;
	private List<Creature> creatureList;
	
	public WorldControler(int size,float roughness,long seed, int creatureCount){
		this.grid = new Grid(size,roughness,seed);
	}

	public Color getTileColour(int i, int j) {
		return grid.getTileColour(i, j);
	}

	public int getSize() {
		return grid.getNumCols();
	}

	/**
	 * Function called every timer tick.
	 * Represents a "game turn" in which every creature moves forward (for now)/
	 * @return
	 */
	public boolean simulateForward() {
		
		for(Creature c : creatureList){
			if (c.move()){
				return true;
			}
			return false;
		}
	}
} 