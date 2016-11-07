package model;

import java.util.Random;

import utils.Utils;

public class Creature implements Cloneable{
	private int id;
	private int x;
	private int y;
	private int energy;
	private int speed;
	private static final int MAXSPEED = 6;
	private static final int MINSPEED = 1;
	
	public Creature(int id, int x, int y){
		this.id = id;
		this.x = x;
		this.y = y;
		this.energy = 50;
		this.speed = 3;
	}
	
	protected Creature(int id, int x, int y, int speed){
		this(id, x, y);
		this.speed = speed;
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

	public int getEnergy() {
		return energy;
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
		energy+=3;
		return true;
	}
	
	public boolean move(int x, int y){
		this.x = x;
		this.y = y;
		this.energy--;
		return true;
	}

	@Override
	public String toString() {
		return "Creature [id=" + id + ", x=" + x + ", y=" + y + ", foodLevel=" + energy + ", speed=" + speed + "]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Creature(id, x, y,  speed);
	}
	
	public Creature reproduce() throws CloneNotSupportedException{
		if(this.energy>150){
			Creature c = (Creature)this.clone();
			c.mutate();
			this.energy -=(50+this.energy/5);
			return c;
		}else{
			return null;
		}
		
	}

	private void mutate() {
		Random rand = new Random();
		if(rand.nextInt(10)==0){
			speed+=rand.nextInt(3)-1;
			speed = Utils.borderVar(speed, MINSPEED, MAXSPEED, 0);
		}
	}
	
}
