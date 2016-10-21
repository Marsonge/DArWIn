package controler;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import model.Creature;
import model.grid.Grid;
import model.grid.Tile;
import utils.UpdateInfoWrapper;

/**
 * General Controler
 *
 */
public class WorldControler extends Observable{
	private Grid grid;
	private int tilesize;
	private List<Creature> creatureList;
	private int tileSize;
	
	public WorldControler(int size,int tilesize, float roughness,long seed, int creatureCount){
		this.tileSize = tilesize;
		this.grid = new Grid(size,roughness,seed);
		this.notifyObservers(this.creatureList); 
		creatureList = new LinkedList<Creature>();
		Random rand = new Random();
		for(int i=0; i<creatureCount;i++){
			creatureList.add(new Creature(i,rand.nextInt(size*this.tileSize),rand.nextInt(size*this.tileSize)));
		}
		this.tilesize = tilesize;
		
	}
	/**
	 *  
	 * @param i
	 * @param j
	 * @return the colour of the tile at the position i, j
	 */
	public Color getTileColour(int i, int j) {
		return grid.getTileColour(i, j);
	}
	
	/**
	 * 
	 * @return size of the map (width, height)
	 */
	public int getSize() {
		return grid.getNumCols();
	}

	/**
	 * Function called every timer tick.
	 * Represents a "game turn" in which every creature moves forward (for now)/
	 * @return
	 */
	public boolean simulateForward() {
		List<Tile> tileList = new LinkedList<>();
		for(Creature c : creatureList){
			this.move(c);
			Tile t = this.eat(c);
			if (t!=null){
				tileList.add(t);
			}
		}
		UpdateInfoWrapper wrapper = new UpdateInfoWrapper(this.creatureList,tileList);
		this.notifyObservers(wrapper); 
		return true;
	}
	
	public boolean move(Creature c){
		return c.move();
	}
	
	/**
	 * Eating mechanism : giving a creature, the controller sets
	 * the color of the associated tile and increments the creature's food level.
	 * @param Creature : the creature eating
	 * @return
	 */
	public Tile eat(Creature creature){
		int tileX = creature.getX();
		int tileY = creature.getY();
		Color tileColor = grid.getTileColour(tileX/this.tileSize, tileY/this.tileSize);
		
		// Check if there is still some food on the tile
		// and that the tile is not sand
		if(tileColor.getGreen() > 140 && tileColor.getRed() < 240){
			creature.eat();
			// repaint tile with lighter green (means less food !)
			return grid.getTile(tileX, tileY);
		}
		return null;
	}
	
	@Override
	public void	notifyObservers(Object arg) {
		super.setChanged();
		super.notifyObservers(arg); 
	}


	@Override
	public void addObserver(Observer o){
		super.addObserver(o);
	}
	public int getTileSize() {
		return tilesize;
	}
	
} 