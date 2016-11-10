package controler;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
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
	private int softcap;
	private int hardcap;
	
	public WorldControler(int size,int tilesize, float roughness,long seed, int creatureCount){
		this.tileSize = tilesize;
		this.grid = new Grid(size,roughness,seed);
		this.notifyObservers(this.creatureList); 
		creatureList = new LinkedList<Creature>();
		Random rand = new Random();
		for(int i=0; i<creatureCount;i++){
			Creature c = new Creature(i,rand.nextInt(size*this.tileSize),rand.nextInt(size*this.tileSize));
			c.initializeNetwork(rand);
			creatureList.add(c);
		}
		this.softcap = 150;
		this.hardcap = 200;
	}
	/**
	 *  
	 * @param i
	 * @param j
	 * @return the color of the tile at the position i, j
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
		//Minimum energy required to survive depends on softcap
		int minenergy;
		int nbCreature = creatureList.size();
		if(nbCreature>softcap*1.5){ // Really hard to live there huh?
			minenergy = 30;
		}else if(nbCreature>softcap){ //Life is tough but fair
			minenergy = 15;
		}else{ //Easy mode
			minenergy = 0;
		}
		
		for(ListIterator<Creature> iterator = this.creatureList.listIterator(); iterator.hasNext();){
			Creature c = iterator.next();
			compute(c);
			if(c.getEnergy() <= minenergy){
				// creature dies
				iterator.remove();
			} else {
				Creature baby = this.reproduce(c);
				//Babies aren't added to the list if we reached the hardcap
				if (baby != null && nbCreature<hardcap){
					iterator.add(baby);
				}
				this.move(c);
				this.eat(c);
				
			}
			
		}
		UpdateInfoWrapper wrapper = new UpdateInfoWrapper(this.creatureList,tileList);
		this.notifyObservers(wrapper); 
		return true;
	}
	
	private void compute(Creature creature) {
		int cx = creature.getX();
		int cy = creature.getY();
		int tileX = cx/this.tileSize;
		int tileY = cy/this.tileSize;
		tileX = Utils.borderVar(tileX, 0, grid.getNumCols()-1, 0);
		tileY = Utils.borderVar(tileY, 0, grid.getNumRows()-1, 0);
		Color tileColor = grid.getTileColour((tileX), (tileY));
		int input[] = new int[15];
		input[0] = tileColor.getRed();
		input[1] = tileColor.getGreen();
		input[2] = tileColor.getBlue();
		
		int i = 0;
		int j = 3;
		
		int[] inputMinusX = getColorArray(tileX-1, tileY);
		for(i=0;i<3;i++){
			input[j] = inputMinusX[i];
			j++;
		}
		int[] inputPlusX = getColorArray(tileX+1,tileY);
		for(i=0;i<3;i++){
			input[j] = inputPlusX[i];
			j++;
		}
		int[] inputMinusY = getColorArray(tileX, tileY-1);
		for(i=0;i<3;i++){
			input[j] = inputMinusY[i];
			j++;
		}
		int[] inputPlusY = getColorArray(tileX,tileY+1);
		for(i=0;i<3;i++){
			input[j] = inputPlusY[i];
			j++;
		}
		creature.compute(input);
	}
	
	/**
	 * 
	 * @param tileX The tile coordinate you want to get the colors off
	 * @param tileY The tile coordinate you want to get the colors off
	 * @return An int[3] array containing the red, green, and blue value of the tile's color
	 */
	private int[] getColorArray(int tileX, int tileY) {
		tileX = Utils.borderVar(tileX, 0, grid.getNumCols()-1, 0);
		tileX /= this.tileSize;
		tileY = Utils.borderVar(tileY, 0, grid.getNumCols()-1, 0);
		tileY /= this.tileSize;
		Color tileColor = grid.getTileColour((tileX), (tileY));
		int colorArray[] = new int[3];
		colorArray[0] = tileColor.getRed();
		colorArray[1] = tileColor.getGreen();
		colorArray[2] = tileColor.getBlue();
		return colorArray;
	}
	
	/**
	 * Grows food on fertile land, adding more green in the color of the tile.
	 */
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
					g+=3;
				}
			}
			else{//Mountain
				if(g<130 && grows == 19){ //Mountains are harsh places : food doesn't always grow
					g+=2;
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
		if(tileColor.getGreen() > 115 && tileColor.getRed() < 200){
			creature.eat();
			// repaint tile with lighter green (means less food !)
			int r = tileColor.getRed();
			int g  = tileColor.getGreen() - 5;
			int b = tileColor.getBlue();
			grid.getTile(tileX, tileY).setColor(new Color(r,g,b));
		}
	}
	
	/**
	 * Moves a creature and blocks it from going out of the borders
	 * @param c the creature
	 * @return true
	 */
	public boolean move(Creature c){
		int x = c.getX();
		int y = c.getY();
		int rot = c.getRot();
		double rad = Math.toRadians(rot);
		float speed = c.getSpeed();
		int newX = (int) Math.round((Math.cos(rad)*speed + x));
		int newY = (int) Math.round((Math.sin(rad)*speed + y));
		newX = Utils.wrappingBorderVar(newX, 0, grid.getNumCols()*tileSize, 5);
		newY = Utils.wrappingBorderVar(newY, 0, grid.getNumRows()*tileSize, 5);
		c.move(newX,newY);
		return true;
	}
	
	/**
	 * reproduce
	 * 
	 * @param creature
	 * @return
	 */
	public Creature reproduce(Creature creature){
		
		Creature child;
		
		/* Reproduces current creature */
		try{
			child = creature.reproduce();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return child;
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