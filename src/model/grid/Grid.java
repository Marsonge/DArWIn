package model.grid;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
/**
 * The grid which contains all Tiles
 * Generated with fractals
 *
 */
public class Grid {
	
	private final int NUMCOLS;
	private final int NUMROWS;
	private final Tile[][] tileGrid;
	private final NoiseGrid noiseGrid;
	private final List<Tile> fertileLand;
	/**
	 * 
	 * @param size number of tiles of the map (height and width)
	 * @param e roughness parameter (entropy)
	 * @param seed used to create the map randomly
	 */
	public Grid(int size,float e,long seed){
		this.tileGrid = new Tile[size][size];
		this.fertileLand = new LinkedList<Tile>();
		this.NUMCOLS = size;
		this.NUMROWS = size;
		Random r = new Random();
		if(seed != 0){
			r.setSeed(seed);
		}
		else{
			r = null;
		}
		noiseGrid = new NoiseGrid(r, e, size, size);
		noiseGrid.initialise();
		float[][] terrainNoiseGrid = noiseGrid.getNoiseGrid();
        for (int i = 0; i < NUMROWS; i++) {
            for (int j = 0; j < NUMCOLS; j++) {
            	if(terrainNoiseGrid[i][j] > 1.5*e){
                    this.tileGrid[i][j] = new Tile(Terrain.SNOW,i,j);
            	}
            	else if(terrainNoiseGrid[i][j] > e){
                    this.tileGrid[i][j] = new Tile(Terrain.MOUNTAINS,i,j);
                    fertileLand.add(this.tileGrid[i][j]);
            	}
            	else if(terrainNoiseGrid[i][j] > e/6){
                    this.tileGrid[i][j] = new Tile(Terrain.WOODS,i,j);
                    fertileLand.add(this.tileGrid[i][j]);
            	}
            	else if (terrainNoiseGrid[i][j] > 0){
	            		this.tileGrid[i][j] = new Tile(Terrain.SAND,i,j);
        		}
            	else if(terrainNoiseGrid[i][j] > -0.3*e){
                    this.tileGrid[i][j] = new Tile(Terrain.SHALLOW_WATER,i,j);
            	}
            	else if(terrainNoiseGrid[i][j] > -1*e){
            		this.tileGrid[i][j] = new Tile(Terrain.OCEAN,i,j);
            	}
            	else{
            		this.tileGrid[i][j] = new Tile(Terrain.DEEP_WATER,i,j);
            	}
            	
            }
        }
	}

	public int getNumCols() {
		return NUMCOLS;
	}

	public int getNumRows() {
		return NUMROWS;
	}

	public Tile getTile(int x, int y){
		return this.tileGrid[x][y];
	}
	
	/**
	 *  
	 * @param i
	 * @param j
	 * @return the colour of the tile at the position i, j
	 */
	public Color getTileColour(int i, int j) {

		return tileGrid[i][j].getColor();
	}
	public List<Tile> getFertileLand(){
		return fertileLand;
	}

}
