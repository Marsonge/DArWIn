package controler;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import model.Creature;
import model.grid.Grid;
/**
 * General Controler
 * Used to manipulate the model
 *
 */
public class WorldControler extends Observable{
	private Grid grid;
	private int tilesize;
	private List<Creature> creatureList;
	
	public WorldControler(int size,int tilesize, float roughness,long seed, int creatureCount){
		this.grid = new Grid(size,roughness,seed);
		this.notifyObservers(this.creatureList); 
		creatureList = new LinkedList<Creature>();
		Random rand = new Random();
		for(int i=0; i<creatureCount;i++){
			creatureList.add(new Creature(i,rand.nextInt(size*tilesize),rand.nextInt(size*tilesize)));
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
		for(Creature c : creatureList){
			this.move(c);
		}
		this.notifyObservers(this.creatureList); 
		return true;
	}
	
	/**
	 * Moves a creature and blocks it from going out of the borders
	 * @param c the creature
	 * @return true
	 */
	public boolean move(Creature c){
		//TODO : Implement a better move
		Random rand = new Random();
		int x = c.getX();
		int y = c.getY();
		int speed = c.getSpeed();
		switch(rand.nextInt(4)){
			case 0:
				x-=speed;
				break;
			case 1:
				x+=speed;
				break;
			case 2:
				y-=speed;
				break;
			case 3:
				y+=speed;
		}
		x = borderVar(x, 0, grid.getNumCols()*tilesize);
		y = borderVar(y, 0, grid.getNumRows()*tilesize);
		c.move(x,y);
		return true;
	}
	
	private int borderVar(int var, int min, int max){
		if(var<min) return min;
		if(var>max) return max;
		return var;
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