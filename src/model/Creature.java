package model;

import java.util.Random;

public class Creature {
	private int id;
	private int x;
	private int y;
	private int foodLevel;
	private int speed;
	
	public Creature(int id, int x, int y){
		this.id = id;
		this.x = x;
		this.y = y;
		this.foodLevel = 50;
		this.speed = 3;
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
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean eat(){
		foodLevel++;
		return true;
	}
	
	public boolean move(){
		//TODO : Implement a better move?
		Random rand = new Random();
		switch(rand.nextInt(4)){
			case 0:
				x-=speed;
				break;
			case 1:
				x+=speed;
				break;
			case 2:
				y-=speed;
				break;
			case 3:
				y+=speed;
		}
		return true;
	}
	
}
