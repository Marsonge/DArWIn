package darwin.darwin.model.grid;

import java.awt.Color;
import java.util.Arrays;
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
	private int seed;
	/**
	 * 
	 * @param size number of tiles of the map (height and width)
	 * @param e roughness parameter (entropy)
	 * @param seed used to create the map randomly
	 */
	public Grid(int size,float e,int seed,Float[] depths){
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
		noiseGrid = new NoiseGrid(r, e, size);
		noiseGrid.initialise();
		if(noiseGrid.getSeed()!=0){
			this.seed = noiseGrid.getSeed();
		}
		else{
			this.seed = seed;
		}
		double[][] terrainNoiseGrid = noiseGrid.getNoiseGrid();
		double max = Arrays.stream(terrainNoiseGrid).parallel().flatMapToDouble(a -> Arrays.stream(a)).max().getAsDouble();
		double min = Arrays.stream(terrainNoiseGrid).parallel().flatMapToDouble(a -> Arrays.stream(a)).min().getAsDouble();

        for (int i = 0; i < NUMROWS; i++) {
            for (int j = 0; j < NUMCOLS; j++) {
            	double result = (terrainNoiseGrid[i][j] - min)/(max-min);
            	if(result > depths[0]){
                    this.tileGrid[i][j] = new Tile(Terrain.SNOW,i,j);
            	}
            	else if(result > depths[1]){
                    this.tileGrid[i][j] = new Tile(Terrain.MOUNTAINS,i,j);
                    fertileLand.add(this.tileGrid[i][j]);
            	}
            	else if(result > depths[2]){
                    this.tileGrid[i][j] = new Tile(Terrain.WOODS,i,j);
                    fertileLand.add(this.tileGrid[i][j]);
            	}
            	else if (result > depths[3]){
	            		this.tileGrid[i][j] = new Tile(Terrain.SAND,i,j);
        		}
            	else if(result > depths[4]){
                    this.tileGrid[i][j] = new Tile(Terrain.SHALLOW_WATER,i,j);
            	}
            	else if(result > depths[5]){
            		this.tileGrid[i][j] = new Tile(Terrain.OCEAN,i,j);
            	}
            	else if(result > depths[6] || depths[6] == 0){
            		this.tileGrid[i][j] = new Tile(Terrain.DEEP_WATER,i,j);
            	}
            	else{
            		this.tileGrid[i][j] = new Tile(Terrain.ETHER,i,j);
            	}
            }
        }
	}

	public Grid(int size,Color[][] tiles){
		this.tileGrid = new Tile[size][size];
		this.fertileLand = new LinkedList<Tile>();
		this.NUMCOLS = size;
		this.NUMROWS = size;
		for(int i = 0;i<NUMROWS;i++){
			for(int j = 0;j<NUMCOLS;j++){
				if(tiles[i][j].getRGB() == Terrain.SNOW.getRGB()){
					tileGrid[i][j] = new Tile(Terrain.SNOW,i,j);
				}
				else if(tiles[i][j].getRGB() == Terrain.MOUNTAINS.getRGB() || 
				  (tiles[i][j].getBlue() == Terrain.MOUNTAINS.getBlueValue() 
				  && tiles[i][j].getRed() == Terrain.MOUNTAINS.getRedValue() 
				  && tiles[i][j].getGreen() <= 135 && tiles[i][j].getGreen() >= 110)){
					tileGrid[i][j] = new Tile(Terrain.MOUNTAINS,tiles[i][j].getRed(),tiles[i][j].getGreen(),tiles[i][j].getBlue(),i,j);
					fertileLand.add(this.tileGrid[i][j]);

				}
				else if(tiles[i][j].getRGB() == Terrain.WOODS.getRGB() || 
					   (tiles[i][j].getBlue() == Terrain.WOODS.getBlueValue() 
					   && tiles[i][j].getRed() == Terrain.WOODS.getRedValue() 
					   && tiles[i][j].getGreen() <= 185 && tiles[i][j].getGreen() >= 110)){
					tileGrid[i][j] = new Tile(Terrain.WOODS,tiles[i][j].getRed(),tiles[i][j].getGreen(),tiles[i][j].getBlue(),i,j);
					fertileLand.add(this.tileGrid[i][j]);

				}
				else if(tiles[i][j].getRGB() == Terrain.SAND.getRGB()){
					tileGrid[i][j] = new Tile(Terrain.SAND,i,j);
				}
				else if(tiles[i][j].getRGB() == Terrain.SHALLOW_WATER.getRGB()){
					tileGrid[i][j] = new Tile(Terrain.SHALLOW_WATER,i,j);
				}
				else if(tiles[i][j].getRGB() == Terrain.OCEAN.getRGB()){
					tileGrid[i][j] = new Tile(Terrain.OCEAN,i,j);
				}
				else if(tiles[i][j].getRGB() == Terrain.DEEP_WATER.getRGB()){
					tileGrid[i][j] = new Tile(Terrain.DEEP_WATER,i,j);
				}
				else{
					tileGrid[i][j] = new Tile(Terrain.ETHER,i,j);
				}
			}
		}
		noiseGrid = null;
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
	public void setTileColour(int i,int j, Color c){
		if(c.getRGB() == Terrain.SNOW.getRGB()){
			tileGrid[i][j] = new Tile(Terrain.SNOW,i,j);
		}
		else if(c.getRGB() == Terrain.MOUNTAINS.getRGB() || 
		  (c.getBlue() == Terrain.MOUNTAINS.getBlueValue() 
		  && c.getRed() == Terrain.MOUNTAINS.getRedValue() 
		  && c.getGreen() <= 130 && c.getGreen() >= 115)){
			tileGrid[i][j] = new Tile(Terrain.MOUNTAINS,c.getRed(),c.getGreen(),c.getBlue(),i,j);
		}
		else if(c.getRGB() == Terrain.WOODS.getRGB() || 
			   (c.getBlue() == Terrain.WOODS.getBlueValue() 
			   && c.getRed() == Terrain.WOODS.getRedValue() 
			   && c.getGreen() <= 180 && c.getGreen() >= 115)){
			tileGrid[i][j] = new Tile(Terrain.WOODS,c.getRed(),c.getGreen(),c.getBlue(),i,j);
		}
		else if(c.getRGB() == Terrain.SAND.getRGB()){
			tileGrid[i][j] = new Tile(Terrain.SAND,i,j);
		}
		else if(c.getRGB() == Terrain.SHALLOW_WATER.getRGB()){
			tileGrid[i][j] = new Tile(Terrain.SHALLOW_WATER,i,j);
		}
		else if(c.getRGB() == Terrain.OCEAN.getRGB()){
			tileGrid[i][j] = new Tile(Terrain.OCEAN,i,j);
		}
		else if(c.getRGB() == Terrain.DEEP_WATER.getRGB()){
			tileGrid[i][j] = new Tile(Terrain.DEEP_WATER,i,j);
		}
		else{
			tileGrid[i][j] = new Tile(Terrain.ETHER,i,j);
		}
	}
	
	public List<Tile> getFertileLand(){
		return fertileLand;
	}

	public int getSeed() {
		return seed;
	}

}
