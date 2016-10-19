package model;

import java.util.Random;

public enum Terrain {
    OCEAN(0, 0, 255),
	MOUNTAINS(130,120,95),
	SAND(245,231,117),
	WOODS(0,140,57),
	FOOD(0,255,0);

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
