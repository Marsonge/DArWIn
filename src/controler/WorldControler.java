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
} 