package model;

import java.awt.Color;
import java.util.Random;

public class Grid {
	
	private final int NUMCOLS;
	private final int NUMROWS;
	private final Tile[][] tileGrid;
	private final NoiseGrid noiseGrid;
	
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

	public Color getTileColour(int i, int j) {

		return tileGrid[i][j].getColour();
	}

}
