package model;

public enum Terrain {
    OCEAN(0, 0, 255), //Blue
	MOUNTAINS(130,120,95), //Brownish
	SAND(245,231,117), //Yellowish
	WOODS(0,140,57), //Darkish Green
	FOOD(0,255,0), //Pure Green of Pain
	SNOW(241,255,255), //Bluish White
	SHALLOW_WATER(0,70,255), //Lighter Blue
	DEEP_WATER(20,0,180); //Dark blue

    private int red;
    private int green;
    private int blue;

    private Terrain(int r, int g, int b) {
        this.red = r;
        this.green = g;
        this.blue = b;
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

    @Override
    public String toString() {
        return red + "," + green + "," + blue;
    }
}
