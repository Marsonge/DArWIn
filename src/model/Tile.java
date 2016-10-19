package model;

import java.awt.Color;

public class Tile {

	private Color color;
	private Terrain t;
	
	public Tile(Terrain t){
		this.color = new Color(t.getRedValue(),t.getGreenValue(),t.getBlueValue());
		this.t = t;
	}
	
	public Color getColour() {
		return color;
	}
	public Terrain getTerrain(){
		return t;
	}

}
