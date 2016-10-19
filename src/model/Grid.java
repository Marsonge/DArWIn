package model;

import utils.*;

import java.awt.Color;

public class Grid {
	
	private final int NUMCOLS;
	private final int NUMROWS;
	private final Tile[][] tileGrid;
	private final NoiseGrid noiseGrid;
	
	public Grid(int rows,int cols,float e){
		this.tileGrid = new Tile[rows][cols];
		this.NUMCOLS = cols;
		this.NUMROWS = rows;
		
		noiseGrid = new NoiseGrid(null, e, rows, cols);
		noiseGrid.initialise();
		float[][] terrainNoiseGrid = noiseGrid.getNoiseGrid();
        for (int i = 0; i < NUMROWS; i++) {
            for (int j = 0; j < NUMCOLS; j++) {
            	if(terrainNoiseGrid[i][j] > e){
                    this.tileGrid[i][j] = new Tile(Terrain.MOUNTAINS);
            	}
            	else if(terrainNoiseGrid[i][j] > e/5){
                    this.tileGrid[i][j] = new Tile(Terrain.WOODS);
            	}
            	else if (terrainNoiseGrid[i][j] > 0){
                    this.tileGrid[i][j] = new Tile(Terrain.SAND);
            	}
            	else{
                    this.tileGrid[i][j] = new Tile(Terrain.OCEAN);
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
