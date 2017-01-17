package darwin.darwin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.Collection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import darwin.darwin.controler.WorldControler;
import darwin.darwin.model.Creature;
import darwin.darwin.model.NeuralNetwork;

public class Export {

	/**
	 * exportToJson
	 * @param file
	 * @param wc
	 * @param listeCreatures
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
		
	public static void exportToJson(File file, WorldControler wc, List<Creature> listeCreatures) throws IOException{

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

	/**
	 * exportToZip
	 * @param selectedFile
	 * @param wc
	 */
	public static void exportToZip(File selectedFile, WorldControler wc) {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

		ZipOutputStream out;
		try {
			out = new ZipOutputStream(new FileOutputStream(selectedFile));


			// Ajout du fichier image
			File image = new File("DArWIn_export_" + sdf.format(cal.getTime()) + ".png");

			wc.exportToPng(image);


			ZipEntry imageZip = new ZipEntry("DArWIn_export_" + sdf.format(cal.getTime()) + ".png");
			out.putNextEntry(imageZip);
			
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(image);
				byte[] byteBuffer = new byte[1024];
				int bytesRead = -1;
				while ((bytesRead = fis.read(byteBuffer)) != -1) {
					out.write(byteBuffer, 0, bytesRead);
				}
				out.flush();
			} finally {
				try {
					fis.close();
				} catch (Exception e3) {
				}
			}
			out.closeEntry();

			// Ajout du fichier JSON
			File json = new File("DArWIn_export_" + sdf.format(cal.getTime()) + ".json");
			exportToJson(json, wc, wc.getCreatureList());


			ZipEntry jsonZip = new ZipEntry("DArWIn_export_" + sdf.format(cal.getTime()) + ".json");
			
			out.putNextEntry(jsonZip);
			FileInputStream fis2 = null;
			try {
				fis2 = new FileInputStream(json);
				byte[] byteBuffer = new byte[1024];
				int bytesRead = -1;
				while ((bytesRead = fis2.read(byteBuffer)) != -1) {
					out.write(byteBuffer, 0, bytesRead);
				}
				out.flush();
			} finally {
				try {
					fis2.close();
				} catch (Exception e3) {
				}
			}

			out.closeEntry();

			out.finish();
			out.close();
			
			Files.deleteIfExists(Paths.get(json.getPath()));
			Files.deleteIfExists(Paths.get(image.getPath()));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}


}
