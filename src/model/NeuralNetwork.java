package model;

import java.util.Arrays;
import java.util.Random;

import utils.Utils;

public class NeuralNetwork {
	
	private final int NB_INPUT = 3;
	private final int NB_OUTPUT = 2;
	private final int NB_HIDDENNODES = 3;
	private float[] matrix;
	private float[][] inputAxiom;
	private float[][] outputAxiom;
	private static Random rand = new Random();

	
	public NeuralNetwork(){
		inputAxiom = new float[NB_HIDDENNODES][NB_INPUT];
		outputAxiom = new float[NB_OUTPUT][NB_HIDDENNODES];

		matrix = new float[NB_INPUT];
	}
	public NeuralNetwork(NeuralNetwork nn){
		this.matrix = new float[NB_INPUT];
		this.inputAxiom = Utils.deepCopyFloatMatrix(nn.getInputAxiom());
		this.outputAxiom = Utils.deepCopyFloatMatrix(nn.getOutputAxiom());
		mutateInput();
		mutateOutput();
	}
	
	private float[][] getInputAxiom() {
		return inputAxiom;
	}
	private float[][] getOutputAxiom() {
		return outputAxiom;
	}
	private float[][] mutateOutput() {
		int i,j;
		for(i=0;i<NB_OUTPUT;i++){
			for(j=0;j<NB_HIDDENNODES;j++){
				if(rand.nextInt(10) == 9){
					outputAxiom[i][j] += (float) ((rand.nextFloat() - 0.5)); //Grosse mutation
				}
				else{
					if(rand.nextInt(3) == 2){
						outputAxiom[i][j] += (float) ((rand.nextFloat()/2 - 0.25)); //Petite mutation
					}
					else{
						outputAxiom[i][j] += (float) ((rand.nextFloat()/10 - 0.05)); //Mutation bénigne
					}
				}
				if(outputAxiom[i][j]<-1)
					outputAxiom[i][j] = -1;
				if(outputAxiom[i][j]>1)
					outputAxiom[i][j] = 1;
			}
		}
		return outputAxiom;
	}
	private float[][] mutateInput() {
		int i,j;
		for(i=0;i<NB_HIDDENNODES;i++){
			for(j=0;j<NB_INPUT;j++){
				if(rand.nextInt(10) == 9){
					inputAxiom[i][j] += (float) ((rand.nextFloat() - 0.5)); //Grosse mutation
				}
				else{
					if(rand.nextInt(3) == 2){
						inputAxiom[i][j] += (float) ((rand.nextFloat()/2 - 0.25)); //Petite mutation
					}
					else{
						inputAxiom[i][j] += (float) ((rand.nextFloat()/10 - 0.05)); //Mutation bénigne
					}
				}
				if(inputAxiom[i][j]<-1)
					inputAxiom[i][j] = -1;
				if(inputAxiom[i][j]>1)
					inputAxiom[i][j] = 1;
			}
		}
		return inputAxiom;
	}
	public void initialise(Random rand) {
		int i,j;
		for(i=0;i<NB_HIDDENNODES;i++){
			for(j=0;j<NB_INPUT;j++){
				this.inputAxiom[i][j] = rand.nextFloat()*2 -1; //Gives a random value between -1 and 1
			}
		}
		for(i=0;i<NB_OUTPUT;i++){
			for(j=0;j<NB_HIDDENNODES;j++){
				this.outputAxiom[i][j] = rand.nextFloat()*2 -1;
			}
		}
	}
	
	public float[] compute(float[] input){
		float output[] = new float[NB_OUTPUT];
		int i,j;
		for(i=0;i<NB_HIDDENNODES;i++){
			float result = 0;
			for(j=0;j<NB_INPUT;j++){
				result += this.inputAxiom[i][j]*input[j];
			}
			matrix[i] =  result;

		}
		for(i=0;i<NB_OUTPUT;i++){
			float result = 0;
			for(j=0;j<NB_HIDDENNODES;j++){
				result += this.outputAxiom[i][j]*matrix[j];
			}
			output[i] = sigmoid(result);
		}
		return output;
	}
	
	private float sigmoid(float f) {
		return (float) (1/(1+Math.exp(-f)));
	}
	@Override
	public String toString() {
		String out = "[";
		int i,j;
		for(i=0;i<NB_HIDDENNODES;i++){
			for(j=0;j<NB_INPUT;j++){
				out += this.inputAxiom[i][j] + ",";
			}
			out += "\n";
		}
		out += "]\n[";
		i = 0;
		j=0;
		for(i=0;i<NB_OUTPUT;i++){
			for(j=0;j<NB_HIDDENNODES;j++){
				out += this.outputAxiom[i][j] + ",";
			}
			out += "\n";
		}
		out += "]\n\n";
		return out;
	}
	
	
}
