package darwin.darwin.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.opencsv.CSVWriter;

import darwin.darwin.controler.WorldControler;
import darwin.darwin.model.grid.Grid;
import darwin.darwin.view.ViewGrid;

public class ExportPNG {

public static void exportToPng(Grid g,File f) throws IOException{
		BufferedImage img = new BufferedImage(g.getNumCols(), g.getNumRows(), BufferedImage.TYPE_INT_RGB);
		for(int i = 0;i<g.getNumCols();i++){
			for(int j = 0;j<g.getNumRows();j++){
				img.setRGB(i, j, g.getTileColour(i,j).getRGB());
			}
		}
		ImageIO.write(img, "png", f);
	}
}
