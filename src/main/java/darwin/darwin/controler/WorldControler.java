package darwin.darwin.controler;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import darwin.darwin.model.Creature;
import darwin.darwin.model.NeuralNetwork;
import darwin.darwin.model.grid.Grid;
import darwin.darwin.model.grid.Terrain;
import darwin.darwin.model.grid.Tile;
import darwin.darwin.utils.IOPng;
import darwin.darwin.utils.UpdateInfoWrapper;
import darwin.darwin.utils.Utils;
import darwin.darwin.view.ViewCreature;

/**
 * General Controler
 *
 */
public class WorldControler extends Observable {

	private Grid grid;
	private int tileSize;
	private int nbdead;
	private int softcap;
	private int hardcap;
	private Creature currentCreature;
	private int seed;
	private Map<Creature, ViewCreature> creatureMap;
	private int countdownGrow;

	public WorldControler(int size, int tilesize, float roughness, int seed, int creatureCount, Float depths[]) {
		this.tileSize = tilesize;
		this.grid = new Grid(size, roughness, seed, depths);
		this.seed = grid.getSeed();
		this.currentCreature = null;
		this.notifyObservers(this.creatureMap);
		creatureMap = new HashMap<Creature, ViewCreature>();
		this.nbdead = 0;
		this.countdownGrow = 0;
		Random rand = new Random();
		for (int i = 0; i < creatureCount; i++) {
			Creature c = new Creature(rand.nextInt(size * this.tileSize), rand.nextInt(size * this.tileSize));
			c.initializeNetwork(rand);
			ViewCreature viewC = new ViewCreature(16, c.getX(), c.getY(), c.getSpeed(), this, null);
			creatureMap.put(c, viewC);
		}
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
	 * Function called every timer tick. Represents a "game turn" in which every
	 * creature moves forward (for now)/
	 * 
	 * @return
	 */
	public boolean simulateForward() {
		// Minimum energy required to survive depends on softcap
		int minenergy;
		int nbCreature = creatureMap.size();
		List<ViewCreature> deadVc = new ArrayList<>();
		Map<Creature, ViewCreature> toAdd = new HashMap<>();
		if (nbCreature > softcap * 1.5) { // Really hard to live there huh?
			minenergy = 30;
		} else if (nbCreature > softcap) { // Life is tough but fair
			minenergy = 15;
		} else { // Easy mode
			minenergy = 0;
		}
		if (countdownGrow == 0) {
			new Thread(new Runnable() {
				public void run() {
					grow();
				}
			}).start();

		}
		System.out.println("countdownGrow : " + countdownGrow);
		countdownGrow = (++countdownGrow) % 2;

		final long then = System.nanoTime();
		for (Iterator<Entry<Creature, ViewCreature>> iterator = this.creatureMap.entrySet().iterator(); iterator
				.hasNext();) {
			Entry<Creature, ViewCreature> e = iterator.next();
			Creature c = e.getKey();
			ViewCreature vc = e.getValue();
			compute(c);
			if (c.getEnergy() <= minenergy) {
				// creature dies
				this.nbdead++;
				iterator.remove();
				deadVc.add(vc);
			} else {
				Creature baby = this.reproduce(c);
				// Babies aren't added to the list if we reached the hardcap
				if (baby != null && nbCreature < hardcap) {
					ViewCreature viewBaby = new ViewCreature(16, baby.getX(), baby.getY(), baby.getSpeed(), this, null);
					toAdd.put(baby, viewBaby);
				}
				this.move(c);
				this.eat(c);
				// update of viewcreature
				vc.setLocation(c.getX(), c.getY());
				vc.setSpeed(c.getSpeed());
				vc.setVisible(true);
				// vc.setSize(16, 16);

			}

		}
		creatureMap.putAll(toAdd);

		final long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - then);
		System.out.println("Time to process all creatures (ms): " + millis);

		final long notifyThen = System.nanoTime();
		UpdateInfoWrapper wrapper = new UpdateInfoWrapper(deadVc, creatureMap.values());
		this.notifyObservers(wrapper);
		final long notifyMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - notifyThen);
		System.out.println("Time to paint all creatures (ms): " + notifyMillis);

		return true;
	}

	private void compute(Creature creature) {
		int cx = creature.getX();
		int cy = creature.getY();
		int tileX = cx / this.tileSize;
		int tileY = cy / this.tileSize;
		tileX = Utils.borderVar(tileX, 0, grid.getNumCols() - 1, 0);
		tileY = Utils.borderVar(tileY, 0, grid.getNumRows() - 1, 0);
		Color tileColor = grid.getTileColour((tileX), (tileY));
		int input[] = new int[15];
		input[0] = tileColor.getRed();
		input[1] = tileColor.getGreen();
		input[2] = tileColor.getBlue();

		int i = 0;
		int j = 3;

		int[] inputMinusX = getColorArray(tileX - 1, tileY);
		for (i = 0; i < 3; i++) {
			input[j] = inputMinusX[i];
			j++;
		}
		int[] inputPlusX = getColorArray(tileX + 1, tileY);
		for (i = 0; i < 3; i++) {
			input[j] = inputPlusX[i];
			j++;
		}
		int[] inputMinusY = getColorArray(tileX, tileY - 1);
		for (i = 0; i < 3; i++) {
			input[j] = inputMinusY[i];
			j++;
		}
		int[] inputPlusY = getColorArray(tileX, tileY + 1);
		for (i = 0; i < 3; i++) {
			input[j] = inputPlusY[i];
			j++;
		}
		creature.compute(input);
	}

	/**
	 * 
	 * @param tileX
	 *            The tile coordinate you want to get the colors off
	 * @param tileY
	 *            The tile coordinate you want to get the colors off
	 * @return An int[3] array containing the red, green, and blue value of the
	 *         tile's color
	 */
	private int[] getColorArray(int tileX, int tileY) {
		tileX = Utils.borderVar(tileX, 0, grid.getNumCols() - 1, 0);
		tileX /= this.tileSize;
		tileY = Utils.borderVar(tileY, 0, grid.getNumCols() - 1, 0);
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
	public void grow() {
		final long then = System.nanoTime();
		List<Tile> fertileLand = grid.getFertileLand();
		for (Tile t : fertileLand) {
			growTile(t);
		}
		final long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - then);
		System.out.println("Time to grow (ms): " + millis);
	}

	private void growTile(Tile t) {
		Color colorTile = t.getColor();
		int r = colorTile.getRed();
		int g = colorTile.getGreen();
		int b = colorTile.getBlue();
		Random rand = new Random();
		int grows = rand.nextInt(20);
		if (Terrain.WOODS.equals(t.getTerrain())) { // Woods, doesn't grow
													// everytime, but still
													// grows from times to times
			if (g < 180 && grows < 4) {
				g += 2;
			}
		} else {// Mountain
			if (g < 130 && grows == 19) { // Mountains are harsh places : food
											// doesn't always grow
				g++;
			}
		}
		t.setColor(new Color(r, g, b));
	}

	/**
	 * Eating mechanism : giving a creature, the controller sets the color of
	 * the associated tile and increments the creature's food level.
	 * 
	 * @param Creature
	 *            : the creature eating
	 * @return
	 */
	public void eat(Creature creature) {
		int cx = creature.getX();
		int cy = creature.getY();
		int tileX = cx / this.tileSize;
		int tileY = cy / this.tileSize;
		tileX = Utils.borderVar(tileX, 0, grid.getNumCols() - 1, 0);
		tileY = Utils.borderVar(tileY, 0, grid.getNumRows() - 1, 0);
		Color tileColor = grid.getTileColour((tileX), (tileY));
		// Check if there is still some food on the tile
		// and that the tile is not sand
		if (tileColor.getGreen() > 115 && tileColor.getRed() < 200) {
			creature.eat();
			// repaint tile with lighter green (means less food !)
			int r = tileColor.getRed();
			int g = tileColor.getGreen() - 5;
			int b = tileColor.getBlue();
			grid.getTile(tileX, tileY).setColor(new Color(r, g, b));
		}
	}

	/**
	 * Moves a creature and blocks it from going out of the borders
	 * 
	 * @param c
	 *            the creature
	 * @return true
	 */
	public boolean move(Creature c) {
		int x = c.getX();
		int y = c.getY();
		int rot = c.getRot();
		double rad = Math.toRadians(rot);
		float speed = c.getSpeed();
		int newX = (int) Math.round((Math.cos(rad) * speed + x));
		int newY = (int) Math.round((Math.sin(rad) * speed + y));
		newX = Utils.wrappingBorderVar(newX, 0, grid.getNumCols() * tileSize, 5);
		newY = Utils.wrappingBorderVar(newY, 0, grid.getNumRows() * tileSize, 5);
		c.move(newX, newY);
		return true;
	}

	/**
	 * reproduce
	 * 
	 * @param creature
	 * @return
	 */
	public Creature reproduce(Creature creature) {

		Creature child;

		/* Reproduces current creature */
		try {
			child = creature.reproduce();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return child;
	}

	public NeuralNetwork getCreatureNn(int x, int y) {
		for (Creature c : creatureMap.keySet()) {
			if (c.getX() == x && c.getY() == y) {
				return c.getNeuralNetwork();
			}
		}
		return null;
	}

	// TODO opti ?
	public int getCreatureEnergy(int x, int y) {
		for (Creature c : creatureMap.keySet()) {
			if (c.getX() == x && c.getY() == y) {
				return c.getEnergy();
			}
		}
		return 0;
	}

	// TODO opti ?
	public float getCreatureSpeed(int x, int y) {
		for (Creature c : creatureMap.keySet()) {
			if (c.getX() == x && c.getY() == y) {
				return c.getSpeed();
			}
		}
		return 0;
	}

	@Override
	public void notifyObservers(Object arg) {
		super.setChanged();
		super.notifyObservers(arg);
	}

	@Override
	public void addObserver(Observer o) {
		super.addObserver(o);
	}

	public int getTileSize() {
		return tileSize;
	}

	public int getCountCreature() {
		return creatureMap.size();
	}

	public int getDeadCountCreature() {
		return this.nbdead;
	}

	public void setSoftCap(int val) {
		this.softcap = val;
	}

	public int getSeed() {
		return seed;
	}

	public Creature getCurrentCreature() {
		return currentCreature;
	}

	public void setCurrentCreature(int x, int y) {
		for (Creature c : creatureMap.keySet()) {
			if (c.getX() == x && c.getY() == y) {
				this.currentCreature = c;
			}
		}
	}

	public Map<Creature, ViewCreature> getCreatureMap() {
		return creatureMap;
	}

	public void setHardCap(int val) {
		this.hardcap = val;
	}

	public void exportToPng(File selectedFile) throws IOException {
		IOPng.exportToPng(grid, selectedFile);
	}

	public void importFromPng(File selectedFile) throws IOException {
		IOPng.importFromPng(grid, selectedFile);
		this.simulateForward();
	}

}