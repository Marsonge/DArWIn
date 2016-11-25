package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

public class Export {

	public static void export(File file) throws IOException{
		
		CSVWriter writer = new CSVWriter(new FileWriter(file), '\t');
		
		String[] entries = "first#second#third".split("#");
	    writer.writeNext(entries);
		writer.close();
	}

}
