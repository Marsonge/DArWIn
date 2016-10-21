package model.grid;

import java.awt.Color;
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
	/**
	 * 
	 * @param size number of tiles of the map (height and width)
	 * @param e roughness parameter (entropy)
	 * @param seed used to create the map randomly
	 */
	public Grid(int size,float e,long seed){
		this.tileGrid = new Tile[size][size];
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
                    this.tileGrid[i][j] = new Tile(Terrain.SNOW);
            	}
            	else if(terrainNoiseGrid[i][j] > e){
                    this.tileGrid[i][j] = new Tile(Terrain.MOUNTAINS);
            	}
            	else if(terrainNoiseGrid[i][j] > e/6){
                    this.tileGrid[i][j] = new Tile(Terrain.WOODS);
            	}
            	else if (terrainNoiseGrid[i][j] > 0){
	            		this.tileGrid[i][j] = new Tile(Terrain.SAND);
        		}
            	else if(terrainNoiseGrid[i][j] > -0.3*e){
                    this.tileGrid[i][j] = new Tile(Terrain.SHALLOW_WATER);
            	}
            	else if(terrainNoiseGrid[i][j] > -1*e){
            		this.tileGrid[i][j] = new Tile(Terrain.OCEAN);
            	}
            	else{
            		this.tileGrid[i][j] = new Tile(Terrain.DEEP_WATER);
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
		return this.tileGrid[y][x];
	}
	
	/**
	 *  
	 * @param i
	 * @param j
	 * @return the colour of the tile at the position i, j
	 */
	public Color getTileColour(int i, int j) {

		return tileGrid[i][j].getColour();
	}

}
