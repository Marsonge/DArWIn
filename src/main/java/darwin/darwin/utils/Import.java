package darwin.darwin.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import darwin.darwin.controler.WorldControler;
import darwin.darwin.model.Creature;
import darwin.darwin.model.NeuralNetwork;

public class Import {

	/**
	 * importFromJson
	 * @param file
	 * @param wc
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static void importFromJson(File file, WorldControler wc) throws IOException{
	
		JSONParser jsonParser = new JSONParser();

		try {
			Object object = jsonParser.parse(new FileReader(file));

			JSONObject obj2 = (JSONObject) object;
			
			//String seed = (String) obj2.get("Seed");
			JSONArray creaturesArray = (JSONArray) obj2.get("Creatures");
		
			int creatureListSize = wc.getCreatureList().size();
			
			// Delete all creatures from list, starting from the end
			for (int j = creatureListSize-1; j >= 0;  j--) {
				wc.getCreatureList().remove(j);
			}
		
			int i = 0;
			for (Object o : creaturesArray){
				
				// Get creature from JSON 
				JSONObject jobj = (JSONObject) o;
				
				long id = (long) jobj.get("id");
				
				long x = (long) jobj.get("x");
				int trueX = (int) x;
				
				long y = (long) jobj.get("y");
				int trueY = (int) y;
				
				long energy = (long) jobj.get("energy");
				int trueEnergy = (int) energy;
				
				double speed = (double) jobj.get("speed");
				float trueSpeed = (float) speed;
			
				
				// Get creature's neural network from JSON
				JSONObject objNeuralNetwork = (JSONObject) jobj.get("neural network");
				
				JSONArray jsonArrayInput = (JSONArray) objNeuralNetwork.get("input_axiom") ;
				JSONArray jsonArrayOutput = (JSONArray) objNeuralNetwork.get("output_axiom") ;

				NeuralNetwork nn = new NeuralNetwork();
				
				JSONObject staticNN = (JSONObject) obj2.get("Static Neural Network");
				
				long nb_input = (long) staticNN.get("input_nodes");
				long hidden_nodes = (long) staticNN.get("hidden_nodes");
				long nb_output = (long) staticNN.get("output_nodes");
				
				float[][] inputAxiom = new float[(int)hidden_nodes][(int)nb_input];
				float[][] outputAxiom = new float[(int)nb_output][(int)hidden_nodes];
				
				int ctrIn1 = 0;
				int ctrIn2 = 0;
				for ( Object obj : jsonArrayInput){
					JSONArray lineArray = (JSONArray) obj;
					for (Object fArray : lineArray){
						inputAxiom[ctrIn1][ctrIn2] = Float.parseFloat(fArray.toString());
						ctrIn2++;
					}
					ctrIn2 = 0;
					ctrIn1++;
				}
								
				int ctr1 = 0;
				int ctr2 = 0;
				for ( Object obj : jsonArrayOutput){
					JSONArray lineArray = (JSONArray) obj;
					for (Object fArray : lineArray){
						outputAxiom[ctr1][ctr2] = Float.parseFloat(fArray.toString());
						ctr2++;
					}
					ctr2 = 0;
					ctr1++;
				}
				
				nn.setInputAxiom(inputAxiom);
				nn.setOutputAxiom(outputAxiom);
				
				// Adding creatures to list
				wc.getCreatureList().add(new Creature(id, trueX, trueY, trueEnergy, trueSpeed, nn));		

				++i;
			}
		
			wc.simulateForward();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} 
	}
	
}
