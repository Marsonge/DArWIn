package model;

import java.awt.Color;
import java.util.Random;

import utils.Utils;

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
	private static final int MINSPEED = 1;
	
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
		this.nn = nn;
	}


	public void initializeNetwork(Random rand){
		this.nn.initialise(rand);
	}
	
	public void compute(int intput[], int inputMinusX[], int inputplusX[], int inputMinusY[], int inputplusY[]){
		
		current = new Color(intput[0],intput[1],intput[2]); 
		xminus = new Color(inputMinusX[0], inputMinusX[1], inputMinusX[2]);
		xplus = new Color(inputplusX[0], inputplusX[1], inputplusX[2]);
		yminus = new Color(inputMinusY[0], inputMinusY[1], inputMinusY[2]);
		yplus = new Color(inputplusY[0], inputplusY[1], inputplusY[2]);
				
		float input[] = new float[3];
		for(int i=0;i<3;i++){//Normalize input
			input[i] = ((float)intput[i])/255;
		}
		int row = 0;//Compute speed
		float result = this.nn.compute(input, row);
		this.speed = (int) Math.round(result*7)%8;

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
		return new Creature(id, x, y,  speed, nn);
	}
	
	public Creature reproduce() throws CloneNotSupportedException{
		if(this.energy>150){
			Creature c = (Creature)this.clone();
			//c.mutate();
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

	public Color getTileColour() {
		return current;
	}

	public Color getXminusColor() {
		return xminus;
	}

	public Color getXplusColor() {
		return xplus;
	}

	public Color getYminusColor() {
		return yminus;
	}

	public Color getYplusColor() {
		return yplus;
	}


}
