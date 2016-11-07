package model;

import java.util.Random;

public class NeuralNetwork {
	
	private final int NB_INPUT = 3;
	private final int NB_OUTPUT = 2;
	private float[][] matrix;
	
	public NeuralNetwork(){
		matrix = new float[NB_INPUT][NB_OUTPUT];
	}
	public NeuralNetwork(float[][] matrix){
		this.matrix = matrix;
	}
	
	public void initialise(Random rand) {
		int i,j;
		for(i=0;i<NB_INPUT;i++){
			for(j=0;j<NB_OUTPUT;j++){
				this.matrix[i][j] = rand.nextFloat();
				System.err.print(this.matrix[i][j] + "  ");
			}
			System.err.println();
		}
		System.err.println();
		System.err.println();
	}
	
	public float compute(float[] input,int row){
		float output = 0;
		int i,j;
		for(i=0;i<NB_INPUT;i++){
			output +=  this.matrix[i][row]*input[i];
		}
		return output;
	}
}
