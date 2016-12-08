package darwin.darwin.model.grid;

import java.awt.Color;

public enum Terrain {
	ETHER(0,0,0), //Pure death
    OCEAN(0, 0, 255), //Blue
	MOUNTAINS(130,120,95), //Brownish
	SAND(245,231,117), //Yellowish
	WOODS(0,140,57), //Darkish Green
	FOOD(0,255,0), //Pure Green of Pain
	SNOW(241,255,255), //Bluish White
	SHALLOW_WATER(0,70,255), //Lighter Blue
	DEEP_WATER(20,0,180); //Dark blue

    private final int red;
    private final int green;
    private final int blue;
    private final int rgb;

    private Terrain(int r, int g, int b) {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.rgb = calculateRGB();
    }

    public int getRedValue() {
        return red;
    }

    public int getBlueValue() {
        return blue;
    }

    public int getGreenValue() {
        return green;
    }
    public int getRGB(){
    	return rgb;
    }
    private int calculateRGB(){
    	Color c = new Color(red,green,blue);
    	return c.getRGB();
    }

    @Override
    public String toString() {
        return red + "," + green + "," + blue;
    }
}
