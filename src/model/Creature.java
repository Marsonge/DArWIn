package model;

public class Creature {
	private int id;
	private int x;
	private int y;
	private int foodLevel;
	
	public Creature(int id, int x, int y){
		this.id = id;
		this.x = x;
		this.y = y;
		this.foodLevel = 50;
	}

	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getFoodLevel() {
		return foodLevel;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
	
}
