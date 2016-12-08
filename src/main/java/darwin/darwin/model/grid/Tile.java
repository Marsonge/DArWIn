package darwin.darwin.model.grid;

import java.awt.Color;

public class Tile {

	private Color color;
	private Terrain t;
	private int x;
	private int y;	
	
	public Tile(Terrain t, int x, int y){
		this.color = new Color(t.getRedValue(),t.getGreenValue(),t.getBlueValue());
		this.t = t;
		this.x = x;
		this.y = y;
	}
	public Tile(Terrain t, int r, int g, int b, int x, int y){
		this.color = new Color(r,g,b);
		this.t = t;
		this.x = x;
		this.y = y;
	}
	
	public void setColor(Color c){
		this.color = c;
	}
	public Color getColor() {
		return color;
	}
	public Terrain getTerrain(){
		return t;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}

}
