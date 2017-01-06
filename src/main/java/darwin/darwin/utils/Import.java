package darwin.darwin.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import darwin.darwin.controler.WorldControler;
import darwin.darwin.model.Creature;
import darwin.darwin.model.NeuralNetwork;

public class Import {

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
				nn.initialise(new Random());
				
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
