package controler;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JPanel;

import model.Creature;
import model.grid.Statistique;
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
			Creature c = new Creature(i,rand.nextInt(size*this.tileSize),rand.nextInt(size*this.tileSize));
			c.initializeNetwork(rand);
			creatureList.add(c);
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
		
		for(ListIterator<Creature> iterator = this.creatureList.listIterator(); iterator.hasNext();){
			Creature c = iterator.next();
			compute(c);
			if(c.getEnergy() <= 0){
				// creature dies
				iterator.remove();
			} else {
				Creature baby = this.reproduce(c);
				if (baby != null){
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
		int input[] = new int[3];
		input[0] = tileColor.getRed();
		input[1] = tileColor.getGreen();
		input[2] = tileColor.getBlue();
		
		int cxminus = Utils.borderVar(creature.getX() - 1, 0, grid.getNumCols()-1, 0);
		int tileXminus = cxminus/this.tileSize;
		Color tileColorMinusX = grid.getTileColour((tileXminus), (tileY));
		int inputMinusX[] = new int[3];
		inputMinusX[0] = tileColorMinusX.getRed();
		inputMinusX[1] = tileColorMinusX.getGreen();
		inputMinusX[2] = tileColorMinusX.getBlue();
		
		int cxplus = Utils.borderVar(creature.getX() + 1, 0, grid.getNumCols()-1, 0);
		int tileXplus = cxplus/this.tileSize;
		Color tileColorPlusX = grid.getTileColour((tileXplus), (tileY));
		int inputplusX[] = new int[3];
		inputplusX[0] = tileColorPlusX.getRed();
		inputplusX[1] = tileColorPlusX.getGreen();
		inputplusX[2] = tileColorPlusX.getBlue();
		
		int cyminus = Utils.borderVar(creature.getY() - 1, 0, grid.getNumCols()-1, 0);
		int tileYminus = cyminus/this.tileSize;
		Color tileColorMinusY = grid.getTileColour((tileX), (tileYminus));
		int inputMinusY[] = new int[3];
		inputMinusY[0] = tileColorMinusY.getRed();
		inputMinusY[1] = tileColorMinusY.getGreen();
		inputMinusY[2] = tileColorMinusY.getBlue();
		
		int cyplus = Utils.borderVar(creature.getY() + 1, 0, grid.getNumCols()-1, 0);
		int tileYplus = cyplus/this.tileSize;
		Color tileColorPlusY = grid.getTileColour((tileX), (tileYplus));
		int inputplusY[] = new int[3];
		inputplusY[0] = tileColorPlusY.getRed();
		inputplusY[1] = tileColorPlusY.getGreen();
		inputplusY[2] = tileColorPlusY.getBlue();
				
		//creature.compute(input);
		creature.compute(input, inputMinusX, inputplusX, inputMinusY,  inputplusY);
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
	
	public int getCountCreature(){
		return creatureList.size();
	}
	
} 