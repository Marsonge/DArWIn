package darwin.darwin.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import darwin.darwin.controler.WorldControler;
import darwin.darwin.model.Creature;
import darwin.darwin.model.NeuralNetwork;

public class Export {

	@SuppressWarnings("unchecked")
	public static void export(File file, WorldControler wc, List<Creature> listeCreatures) throws IOException{
		
		JSONObject obj = new JSONObject();
		
		obj.put("Static Neural Network", NeuralNetwork.getStaticJson());

		JSONArray jsonCreatures = new JSONArray();
		
		for (Creature creature : listeCreatures){
			jsonCreatures.add(creature.toJson());
		}
		
		obj.put("Creatures", jsonCreatures);
		
		FileWriter fileWr = new FileWriter(file);
		fileWr.write(obj.toJSONString());
		fileWr.flush();
		fileWr.close();
		
	}

}
