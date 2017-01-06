package darwin.darwin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
	 * 
	 * @param file
	 * @param wc
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static void importFromJson(File file, WorldControler wc) throws IOException{
	
		JSONParser jsonParser = new JSONParser();

		try {
			
			// Delete all creatures from list, starting from the end
			for (int j = wc.getCreatureList().size()-1; j >= 0;  j--) {
				wc.getCreatureList().remove(j);
			}
			
			// Getting an array of creatures from the JSON
			Object object = jsonParser.parse(new FileReader(file));
			JSONObject obj2 = (JSONObject) object;		
			JSONArray creaturesArray = (JSONArray) obj2.get("Creatures");

			// For each creature on the JSON
			int i = 0;
			for (Object o : creaturesArray){
				
				/**
				 * Creature's values
				 */
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
			
				/**
				 * Static Neural Network values
				 */
				JSONObject staticNN = (JSONObject) obj2.get("Static Neural Network");
				
				// Get the number of input, output and hidden nodes
				long nb_input = (long) staticNN.get("input_nodes");
				long hidden_nodes = (long) staticNN.get("hidden_nodes");
				long nb_output = (long) staticNN.get("output_nodes");
				
				/**
				 * Creature's neural network
				 */
				JSONObject objNeuralNetwork = (JSONObject) jobj.get("neural network");
				
				JSONArray jsonArrayInput = (JSONArray) objNeuralNetwork.get("input_axiom") ;
				JSONArray jsonArrayOutput = (JSONArray) objNeuralNetwork.get("output_axiom") ;

				// Instanciate input/output axiom matrix
				float[][] inputAxiom = new float[(int)hidden_nodes][(int)nb_input];
				float[][] outputAxiom = new float[(int)nb_output][(int)hidden_nodes];
				
				// Recreating input axiom matrix from JSON
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
				
				// Recreating output axiom matrix from JSON		
				int ctrOut1 = 0;
				int ctrOut2 = 0;
				for ( Object obj : jsonArrayOutput){
					JSONArray lineArray = (JSONArray) obj;
					for (Object fArray : lineArray){
						outputAxiom[ctrOut1][ctrOut2] = Float.parseFloat(fArray.toString());
						ctrOut2++;
					}
					ctrOut2 = 0;
					ctrOut1++;
				}
				
				// Recreating creature's neural network
				NeuralNetwork nn = new NeuralNetwork();
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

	/**
	 * importFromZip
	 * @param selectedFile
	 * @param wc
	 */
	public static void importFromZip(File selectedFile, WorldControler wc) {
		
		ZipInputStream zipIn;
		try {
			zipIn = new ZipInputStream(new FileInputStream(selectedFile));
		

		ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
		

        while (entry != null) {
        	
            if (entry.getName().contains(".png")){
            	wc.importFromPng(new File(entry.getName()));
            }
                
            if (entry.getName().contains(".json")){
            	importFromJson(new File(entry.getName()), wc);
            }
            
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
            
        }
   
		zipIn.close();
		
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
