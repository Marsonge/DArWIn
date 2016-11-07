package controler;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import model.Creature;
import model.grid.Grid;
import model.grid.Terrain;
import model.grid.Tile;
import utils.UpdateInfoWrapper;
import utils.Utils;

/**
 * General Controler
 *
 */
public class WorldControler extends Observable{
	private Grid grid;

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
		
		for(Iterator<Creature> iterator = this.creatureList.iterator(); iterator.hasNext();){
			Creature c = iterator.next();
			if(c.getEnergy() <= 0){
				// creature dies
				iterator.remove();
			} else {
				this.reproduce(c);
				this.move(c);
				this.eat(c);
			}
		}
		UpdateInfoWrapper wrapper = new UpdateInfoWrapper(this.creatureList,tileList);
		this.notifyObservers(wrapper); 
		return true;
	}
	
	public void grow(){
		List<Tile> fertileLand = grid.getFertileLand();
		for(Tile t : fertileLand){
			Color colorTile = t.getColor();
			int r = colorTile.getRed();
			int g = colorTile.getGreen();
			int b = colorTile.getBlue();	
			Random rand = new Random();
			int grows = rand.nextInt(20);
			if(Terrain.WOODS.equals(t.getTerrain())){
				if(g<180 && grows < 5){ //Mountains are harsh places : food doesn't always grow
					g++;
				}
			}
			else{//Mountain
				if(g<130 && grows == 19){ //Mountains are harsh places : food doesn't always grow
					g++;
				}
			}
			t.setColor(new Color(r,g,b));

		}
	}
	
	/**
	 * Eating mechanism : giving a creature, the controller sets
	 * the color of the associated tile and increments the creature's food level.
	 * @param Creature : the creature eating
	 * @return
	 */
	public void eat(Creature creature){
		int cx = creature.getX();
		int cy = creature.getY();
		int tileX = cx/this.tileSize;
		int tileY = cy/this.tileSize;
		tileX = Utils.borderVar(tileX, 0, grid.getNumCols()-1, 0);
		tileY = Utils.borderVar(tileY, 0, grid.getNumRows()-1, 0);
		Color tileColor = grid.getTileColour((tileX), (tileY));
		// Check if there is still some food on the tile
		// and that the tile is not sand
		System.out.print(creature);
		if(tileColor.getGreen() > 115 && tileColor.getRed() < 200){
			creature.eat();
			System.out.print("   CRUNCH");
			// repaint tile with lighter green (means less food !)
			int r = tileColor.getRed();
			int g  = tileColor.getGreen() - 5;
			int b = tileColor.getBlue();
			grid.getTile(tileX, tileY).setColor(new Color(r,g,b));
		}
		System.out.println();
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
		x = Utils.borderVar(x, 0, grid.getNumCols()*tileSize, 5);
		y = Utils.borderVar(y, 0, grid.getNumRows()*tileSize, 5);
		c.move(x,y);
		return true;
	}
	
	/**
	 * reproduce
	 * 
	 * @param creature
	 * @return
	 */
	public boolean reproduce(Creature creature){
		
		Creature child;
		
		/* Reproduces current creature */
		try{
			child = creature.reproduce();
		} catch(Exception e){
			return false;
		}
		
		/* If created creature is not null, it's added to the creature list */
		if (child != null) {
			creatureList.add(child);
		} else {
			return false;
		}
		
		return true;
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
		return tileSize;
	}
	
} 