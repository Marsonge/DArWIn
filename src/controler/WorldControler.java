package controler;

import java.awt.Color; 
import java.util.*;

import javax.swing.JPanel;

import model.Creature;
import model.grid.Statistique;
import model.grid.Grid;
import model.grid.Tile;
import utils.UpdateInfoWrapper;
import utils.Utils;

/**
 * General Controler
 *
 */
public class WorldControler extends Observable{
	private Grid grid;
	private Statistique statistique;
	private int tilesize;
	private List<Creature> creatureList;
	private int tileSize;
	
	public WorldControler(int size,int tilesize, float roughness,long seed, int creatureCount){
		this.tileSize = tilesize;
		this.grid = new Grid(size,roughness,seed);
		this.statistique = new Statistique();
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
	 * 
	 * @return 
	 */
	public JPanel getStatistique() {
		return statistique.getStatistique();
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
				this.move(c);
				Tile t = this.eat(c);
				if (t!=null){
					tileList.add(t);
				}
			}
		}
		
		UpdateInfoWrapper wrapper = new UpdateInfoWrapper(this.creatureList,tileList);
		this.notifyObservers(wrapper); 
		return true;
	}
	
	/**
	 * Eating mechanism : giving a creature, the controller sets
	 * the color of the associated tile and increments the creature's food level.
	 * @param Creature : the creature eating
	 * @return
	 */
	public Tile eat(Creature creature){
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
		if(tileColor.getGreen() > 100 && tileColor.getRed() < 100){
			creature.eat();
			System.out.print("   CRUNCH");
			// repaint tile with lighter green (means less food !)
			int r = tileColor.getRed();
			int g  = tileColor.getGreen() - 2;
			int b = tileColor.getBlue();
			grid.getTile(tileX, tileY).setColor(new Color(r,g,b));
			//return grid.getTile(tileX, tileY);
			return null;
		}
		System.out.println();
		return null;
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
	
	public int getCountCreature(){
		return creatureList.size();
	}
	
} 