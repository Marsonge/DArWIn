package controler;

import java.awt.Color; 
import java.util.*;

import javax.swing.JPanel;

import model.Creature;
import model.grid.Statistique;
import model.grid.Grid;

/**
 * General Controler
 * Used to manipulate the model
 *
 */
public class WorldControler extends Observable{
	private Grid grid;
	private Statistique statistique;
	private int tilesize;
	private List<Creature> creatureList;
	
	public WorldControler(int size,int tilesize, float roughness,long seed, int creatureCount){
		this.grid = new Grid(size,roughness,seed);
		this.statistique = new Statistique();
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
		for(Creature c : creatureList){
			c.move();
		}
		this.notifyObservers(this.creatureList); 
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
		return tilesize;
	}
	
	public int getCountCreature(){
		return creatureList.size();
	}
	
} 