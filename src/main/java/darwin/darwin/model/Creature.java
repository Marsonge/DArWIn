package darwin.darwin.model;

import java.awt.Color;
import java.util.Random;

import org.json.simple.JSONObject;

public class Creature implements Cloneable {
	
	Color xminus;
	Color xplus;
	Color current;
	Color yminus;
	Color yplus;
	
	private long id;
	private int x;
	private int y;
	private int energy;
	private double speed;
	private NeuralNetwork nn;
	private int rot = 0;
	private int previousRot = 0;
	private static final int MAXSPEED = 7;
	private static long idmax = 0;
	
	public Creature(int x, int y){
		this.id = idmax++;
		this.x = x;
		this.y = y;
		this.energy = 50;
		this.speed = 3;
		this.nn = new NeuralNetwork();
	}

	/**
	 * Called only for reproduction purpose
	 * 
	 * @param x
	 *            position on x axis
	 * @param y
	 *            position on y axis
	 * @param speed
	 *            initial speed of creature
	 * @param nn
	 *            neural network
	 */
	
	public Creature(long id, int x, int y, int energy, float speed, NeuralNetwork nn){
		this.id = id;
		this.x = x;
		this.y = y;
		this.energy = energy;
		this.speed = speed;
		this.nn = nn;
	}
	
	protected Creature(int x, int y, double speed, NeuralNetwork nn) {
		this(x, y);
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

		double input[] = new double[17];
		// TODO : Normalize correctly ?
		for (int i = 0; i < 15; i++) {// Normalize input : Colors
			input[i] = ((double) intput[i]) / (255);
		}
		input[15] = (float)this.previousRot/180;
		input[16] = (float)this.energy/150;
		double result[] = this.nn.compute(input);
		this.speed = getShortSigmoid(result[0])*MAXSPEED;
		this.rot = (int) (this.rot + ((getLargeSigmoid(result[1])-0.5)*360))%360;
		this.previousRot = (int) (getLargeSigmoid(result[1])-0.5)*360;
	}

	private double getShortSigmoid(double f) {
		return (double) (1 / (1 + Math.exp(-f / 3)));
	}

	private double getLargeSigmoid(double f) {
		return (double) (1 / (1 + Math.exp(-f / 10)));
	}
	public long getId() {
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

	public double getSpeed() {
		return speed;
	}
	
	public NeuralNetwork getNeuralNetwork() {
		return nn;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public boolean eat(){
		energy+=2;
		return true;
	}
	
	public int getRot(){
		return rot;
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
		return new Creature(x, y,  speed, nn);
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
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJson(){
		
		JSONObject jsonThis = new JSONObject();
		
		jsonThis.put("id", this.id);
		jsonThis.put("x", this.x);
		jsonThis.put("y", this.y);
		jsonThis.put("energy", this.energy);
		jsonThis.put("speed", this.speed);
		jsonThis.put("neural network", this.nn.toJson());
		
		return jsonThis;
	}

	/**
	 * updates model inputAxiom and outputAxiom
	 * 
	 * @param inputAxiom
	 * @param outputAxiom
	 */
	public void update(double[][] inputAxiom, double[][] outputAxiom) {
		this.nn.setInputAxiom(inputAxiom);
		this.nn.setOutputAxiom(outputAxiom);
	}
	
}
