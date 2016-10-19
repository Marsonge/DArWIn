package controler;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.Creature;
import model.Grid;

public class WorldControler extends Observable{
	private Grid grid;
	private List<Creature> creatureList;
	
	public WorldControler(int size,int tilesize, float roughness,long seed, int creatureCount){
		this.grid = new Grid(size,roughness,seed);
		this.notifyObservers(this.creatureList); 
		creatureList = new LinkedList<Creature>();
		creatureList.add(new Creature(1,0,0));
		creatureList.add(new Creature(2,0,0));
		creatureList.add(new Creature(3,0,0));
		
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
		return false;
	}
	
	@Override
	public void	notifyObservers(Object arg) {
		super.setChanged();
		super.notifyObservers(arg); 
	}

	/* (non-Javadoc)
	 * @see java.util.Observable#addObserver(java.util.Observer)
	 */
	@Override
	public void addObserver(Observer o){
		super.addObserver(o);
		this.notifyObservers(this.creatureList); 
	}
} 