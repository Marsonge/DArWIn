package model.grid;

import java.util.*;



// Heavily inspired of http://stackoverflow.com/a/5532726 
// http://www.redblobgames.com/maps/terrain-from-noise/#demo

public class NoiseGrid {
	
    private float[][] grid;
    float roughness;
    private Random seed;


    public NoiseGrid(Random seed, float roughness, int width, int height) {
        this.roughness = roughness / width;
        this.grid = new float[width][height];
        this.seed = (seed == null) ? new Random() : seed;
    }
    
    public void initialise() {
        int xh = this.grid.length - 1;
        int yh = this.grid[0].length - 1;

        // set the corner points
        this.grid[0][0] = this.seed.nextFloat() - 0.5f;
        this.grid[0][yh] = this.seed.nextFloat() - 0.5f;
        this.grid[xh][0] = this.seed.nextFloat() - 0.5f;
        this.grid[xh][yh] = this.seed.nextFloat() - 0.5f;

        // generate the fractal
        generate(0, 0, xh, yh);
    }


    // Changes the value of the tile to something slightly different :
    // The objective is to blur or roughen the edges of the map, so that it
    // doesn't look square but natural
    private float roughen(float v, int l, int h) {
        return v + this.roughness * (float) (this.seed.nextGaussian() * (h - l));
    }
    

    // generate the fractal
    private void generate(int xl, int yl, int xh, int yh) {
        int xm = (xl + xh) / 2; // Calculates the middle points
        int ym = (yl + yh) / 2;
        if ((xl == xm) && (yl == ym)) return; // If our middle points is our extremity, we've done everything
        
        this.grid[xm][yl] = 0.49f * (this.grid[xl][yl] + this.grid[xh][yl]); // If you decide to change this value, keep the same
        this.grid[xm][yh] = 0.49f * (this.grid[xl][yh] + this.grid[xh][yh]); // along those 4 lines, and the value between 0 and 1.
        this.grid[xl][ym] = 0.49f * (this.grid[xl][yl] + this.grid[xl][yh]); // The higher the value, the higher the terrain (mountains).
        this.grid[xh][ym] = 0.49f * (this.grid[xh][yl] + this.grid[xh][yh]); // It is EXTREMELY sensitive, so try changing by 0.01 at a time.

        float v = roughen(0.49f * (this.grid[xm][yl] + this.grid[xm][yh]), xl + yl, yh
                + xh);
        this.grid[xm][ym] = v;
        this.grid[xm][yl] = roughen(this.grid[xm][yl], xl, xh);
        this.grid[xm][yh] = roughen(this.grid[xm][yh], xl, xh);
        this.grid[xl][ym] = roughen(this.grid[xl][ym], yl, yh);
        this.grid[xh][ym] = roughen(this.grid[xh][ym], yl, yh);

        generate(xl, yl, xm, ym); // Does the same with 4 squares in our original squares, i.e.
        generate(xm, yl, xh, ym); // in small parts of our maps.
        generate(xl, ym, xm, yh);
        generate(xm, ym, xh, yh);
    }
        
    public float[][] getNoiseGrid(){
    	return grid;
    }
    
}
