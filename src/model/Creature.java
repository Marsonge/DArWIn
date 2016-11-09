package model;

import java.awt.Color;
import java.util.Random;

public class Creature implements Cloneable {
	
	Color xminus;
	Color xplus;
	Color current;
	Color yminus;
	Color yplus;
	
	private int id;
	private int x;
	private int y;
	private int energy;
	private int speed;
	private NeuralNetwork nn;
	private static final int MAXSPEED = 7;
	
	public Creature(int id, int x, int y){
		this.id = id;
		this.x = x;
		this.y = y;
		this.energy = 50;
		this.speed = 3;
		this.nn = new NeuralNetwork();
	}
	
	protected Creature(int id, int x, int y, int speed, NeuralNetwork nn){
		this(id, x, y);
		this.speed = speed;
		this.nn = new NeuralNetwork(nn);
	}


	public void initializeNetwork(Random rand){
		this.nn.initialise(rand);
	}
	
	public void compute(int intput[]){
		
		current = new Color(intput[0],intput[1],intput[2]); 
		xminus = new Color(intput[3], intput[4], intput[5]);
		xplus = new Color(intput[6], intput[7], intput[8]);
		yminus = new Color(intput[9], intput[10], intput[11]);
		yplus = new Color(intput[12], intput[13], intput[14]);
				
		float input[] = new float[15];
		//TODO : Add the other inputs in the loop
		for(int i=0;i<3;i++){//Normalize input : Colors
			input[i] = ((float)intput[i])/255;
		}
		float result[] = this.nn.compute(input);
		this.speed = (int) Math.round((result[0])*(MAXSPEED));

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
		energy+=2;
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
		return "Creature [id=" + id + ", x=" + x + ", y=" + y + ", energy=" + energy + ", speed=" + speed + "]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Creature(id, x, y,  speed, nn);
	}
	
	public Creature reproduce() throws CloneNotSupportedException{
		if(this.energy>150){
			Creature c = (Creature)this.clone();
			this.energy -=(50+this.energy/5);
			return c;
		}else{
			return null;
		}
		
	}

	
}
