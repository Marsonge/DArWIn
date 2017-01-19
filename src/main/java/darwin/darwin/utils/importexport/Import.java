package darwin.darwin.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import darwin.darwin.controler.WorldControler;
import darwin.darwin.model.Creature;
import darwin.darwin.model.NeuralNetwork;
import darwin.darwin.view.ViewCreature;

public class Import {

	/**
	 * importFromJson
	 * 
	 * @param file
	 * @param wc
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static void importFromJson(InputStream file, WorldControler wc) throws IOException{
	
		JSONParser jsonParser = new JSONParser();
		
		ArrayList<ViewCreature> deadList = new ArrayList<ViewCreature>(wc.getCreatureMap().values());

		try {
			
			// Delete all creatures from list, starting from the end
			
			wc.getCreatureMap().clear();
			
			// Getting an array of creatures from the JSON
			BufferedReader streamReader = new BufferedReader(new InputStreamReader(file, "UTF-8"));
			StringBuilder responseStrBuilder = new StringBuilder();

			String inputStr;
			while ((inputStr = streamReader.readLine()) != null)
			    responseStrBuilder.append(inputStr);
			
			Object object = jsonParser.parse(responseStrBuilder.toString());
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
				ViewCreature viewC = new ViewCreature(16, trueX, trueY, trueSpeed, wc, null);

				wc.getCreatureMap().put(new Creature(id, trueX, trueY, trueEnergy, trueSpeed, nn),viewC);		

				++i;
			}
		
			System.out.println("Taille map:" + wc.getCreatureMap().size());
			UpdateInfoWrapper wrapper = new UpdateInfoWrapper(deadList, wc.getCreatureMap().values());
			wc.notifyObservers(wrapper);
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
	@SuppressWarnings("resource")
	public static void importFromZip(File selectedFile, WorldControler wc) {
		
		ZipInputStream zipIn;
		
		try {
			ZipFile zipFile = new ZipFile(selectedFile);
			zipIn = new ZipInputStream(new FileInputStream(selectedFile));
		
			ZipEntry entry = zipIn.getNextEntry();
			// iterates over entries in the zip file
			
			while (entry != null) {
        	
				InputStream stream = zipFile.getInputStream(entry);
				
				if (entry.getName().contains(".png")){
					wc.importFromPng(stream);
				}
                
				if (entry.getName().contains(".json")){
					importFromJson(stream, wc);
				}
				
				zipIn.closeEntry();
				entry = zipIn.getNextEntry();
				stream.close();
        }
    
		zipIn.close();

		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
