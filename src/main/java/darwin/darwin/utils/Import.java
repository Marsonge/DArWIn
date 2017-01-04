package darwin.darwin.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import darwin.darwin.model.Creature;
import darwin.darwin.model.NeuralNetwork;

public class Import {

	public static void importFromJson(File file) throws IOException{
	
		JSONParser jsonParser = new JSONParser();

		try {
			Object object = jsonParser.parse(new FileReader(file));

			JSONObject obj2 = (JSONObject) object;
			
			//String seed = (String) obj2.get("Seed");
			JSONArray creaturesArray = (JSONArray) obj2.get("Creatures");
			
			for (Object o : creaturesArray){
				
				// Get jsonobject 
				JSONObject jobj = (JSONObject) o;
				
				Long id = (Long) jobj.get("id");
				Long x = (Long) jobj.get("x");
				Integer trueX = Integer.getInteger(x.toString());
				Long y = (Long) jobj.get("y");
				Integer trueY = Integer.getInteger(y.toString());
				Long energy = (Long) jobj.get("energy");
				Integer trueEnergy = Integer.getInteger(energy.toString());
				Double speed = (Double) jobj.get("speed");
				Float trueSpeed = Float.parseFloat(speed.toString());
			
			}
			
			//System.out.println(seed);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
	
}
