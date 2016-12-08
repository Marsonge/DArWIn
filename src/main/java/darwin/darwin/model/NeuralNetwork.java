package darwin.darwin.model;

import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import darwin.darwin.utils.Utils;


/**
 * 
 * @author Tim
 * This class represents the brain of a creature.
 * It is composed of two axioms and a matrix.
 * The matrix represents the value of the hidden layer of the neural network.
 * The axioms represent the weight of each link of the neural network.
 * 
 * inputAxiom represents the links of the inputs to the hidden layer nodes.
 * outputAxiom represents the links of the hidden layer nodes to the outputs.
 */
public class NeuralNetwork {
	
	private static final int NB_INPUT = 17;
	private static final int NB_OUTPUT = 2;
	private static final int NB_HIDDENNODES = 17;
	private float[] matrix;
	private float[][] inputAxiom;
	private float[][] outputAxiom;
	private static Random rand = new Random();

	
	public NeuralNetwork(){
		inputAxiom = new float[NB_HIDDENNODES][NB_INPUT];
		outputAxiom = new float[NB_OUTPUT][NB_HIDDENNODES];

		matrix = new float[NB_INPUT];
	}
	
	/**
	 * Creates a new neural network, using the axioms of the param nn and mutating them slightly.
	 * @param nn : The neural network to mutate from to create a new one
	 */
	public NeuralNetwork(NeuralNetwork nn){
		this.matrix = new float[NB_INPUT];
		this.inputAxiom = Utils.deepCopyFloatMatrix(nn.getInputAxiom()); //Clones properly a 2D array
		this.outputAxiom = Utils.deepCopyFloatMatrix(nn.getOutputAxiom());
		mutateInput(); 
		mutateOutput();
	}

	/**
	 * Mutates the output axiom. Three different mutations are possible :
	 * -> A big mutation, with a 1/10 chance. It changes the axiom by up to 0.5 or -0.5.
	 * -> A small mutation, with a 3/10 chance. It changes the axiom by up to 0.25 or -0.25.
	 * -> A benign mutation, with a 6/10 chance. It changes the axiom by up to 0.05 or -0.05.
	 * 
	 */
	private void mutateOutput() {
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
						outputAxiom[i][j] += (float) ((rand.nextFloat()/10 - 0.05)); //Mutation b�nigne
					}
				}
				if(outputAxiom[i][j]<-1)
					outputAxiom[i][j] = -1;
				if(outputAxiom[i][j]>1)
					outputAxiom[i][j] = 1;
			}
		}
	}
	
	/**
	 * Mutates the input axiom. Three different mutations are possible :
	 * -> A big mutation, with a 1/10 chance. It changes the axiom by up to 0.5 or -0.5.
	 * -> A small mutation, with a 3/10 chance. It changes the axiom by up to 0.25 or -0.25.
	 * -> A benign mutation, with a 6/10 chance. It changes the axiom by up to 0.05 or -0.05.
	 * 
	 */
	private void mutateInput() {
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
						//inputAxiom[i][j] += (float) ((rand.nextFloat()/10 - 0.05)); //Mutation b�nigne
					}
				}
				if(inputAxiom[i][j]<-1)
					inputAxiom[i][j] = -1;
				if(inputAxiom[i][j]>1)
					inputAxiom[i][j] = 1;
			}
		}
	}
	
	/**
	 * Initialises the axioms of a neural network with random values, between -1 and 1.
	 * @param rand : A random seeded once to get different values no matter the time
	 */
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
	
	/**
	 * Compute an input array to return an output array
	 * @param input : The array of values to input to the neural network
	 * @return output[] : An array of values representing the answers given by the network
	 */
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
			output[i] = result;
		}
		return output;
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
	
	public float[][] getInputAxiom() {
		return inputAxiom;
	}
	public float[][] getOutputAxiom() {
		return outputAxiom;
	}

	public static int getNbInput() {
		return NB_INPUT;
	}

	public static int getNbOutput() {
		return NB_OUTPUT;
	}

	public static int getNbHiddennodes() {
		return NB_HIDDENNODES;
	}

	public JSONObject toJson() {
		
		int i,j;
		JSONObject jsonThis = new JSONObject();
		
		JSONArray jsonArrayInput = new JSONArray();
		JSONArray jsonArrayOutput = new JSONArray();
		
		for(i=0;i<NB_HIDDENNODES;i++){
			JSONArray lineArrayIn = new JSONArray();
			for(j=0;j<NB_INPUT;j++){
				lineArrayIn.add(inputAxiom[i][j]);
			}
			jsonArrayInput.add(lineArrayIn);
		}
		
		for(i=0;i<NB_OUTPUT;i++){
			JSONArray lineArrayOut = new JSONArray();
			for(j=0;j<NB_HIDDENNODES;j++){
				lineArrayOut.add(outputAxiom[i][j]);
			}
			jsonArrayOutput.add(lineArrayOut);
		}
	
		jsonThis.put("input_axiom", jsonArrayInput);
		jsonThis.put("output_axiom", jsonArrayOutput);
		
		return jsonThis;
	}

	public static JSONObject getStaticJson(){
		
		JSONObject jsonStatic = new JSONObject();
		
		jsonStatic.put("input_nodes", NB_INPUT);
		jsonStatic.put("hidden_nodes", NB_HIDDENNODES);
		jsonStatic.put("output_nodes", NB_OUTPUT);
		
		return jsonStatic;
	}
	
	
	
	
}
