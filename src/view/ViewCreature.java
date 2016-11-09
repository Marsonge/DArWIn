package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import model.Creature;

public class ViewCreature extends JLabel {

	private static final long serialVersionUID = 3003074446528565112L;
	private Creature c;
	private Image img;
	private int size;
	
	public ViewCreature(Creature c,int size){
		super();
		this.size = size;
		this.c = c;
		this.setLocation(c.getX(), c.getY());
		this.setSize(size,size);
		try {
		    img = ImageIO.read(new File("resources/img/creature"+c.getSpeed()+".png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		this.img = img.getScaledInstance(size,size,Image.SCALE_SMOOTH); //Resizes the image. Try to keep size a power of 2!
		this.setIcon(new ImageIcon(this.img));

	}

	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x-(size/2), y-(size/2)); //Offsets the label position so that its center corresponds to the creature's coordinates
	}
	@Override
	public void setLocation(Point p) {
		super.setLocation((int)p.getX()-size/2,(int)p.getY()-size/2);
	}
	
		
}
