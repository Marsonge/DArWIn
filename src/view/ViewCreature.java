package view;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controler.WorldControler;
import model.Creature;

public class ViewCreature extends JLabel {

	private static final long serialVersionUID = 3003074446528565112L;
	private Image img;
	private int size;
	private WorldControler wc;
	private int x;
	private int y;
	private ViewCreature self = this;
	
	public ViewCreature(int size, int x, int y, int speed, WorldControler wc){
		super();
		this.size = size;
		this.wc = wc;
		this.setLocation(x, y);
		this.x = x;
		this.y = y;
		this.setSize(size,size);
		try {
		    img = ImageIO.read(new File("resources/img/creature"+speed+".png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		this.img = img.getScaledInstance(size,size,Image.SCALE_SMOOTH); //Resizes the image. Try to keep size a power of 2!
		this.setIcon(new ImageIcon(this.img));
		this.addMouseListener(new CreatureMouseListener());
	}

	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x-(size/2), y-(size/2)); //Offsets the label position so that its center corresponds to the creature's coordinates
	}
	@Override
	public void setLocation(Point p) {
		super.setLocation((int)p.getX()-size/2,(int)p.getY()-size/2);
	}

	/**
	 * Custom mouse listener intern class for clicking on creature on screen in order to get info
	 * @author lucie.deruaz
	 *
	 */
	class CreatureMouseListener implements MouseListener{
	   public void mouseClicked(MouseEvent e) {
		   //TODO display creature info
		   wc.getCreatureInfo(x, y);
	   }

	   public void mousePressed(MouseEvent e) {
	   }

	   public void mouseReleased(MouseEvent e) {
	   }

	   public void mouseEntered(MouseEvent e) {
		   self.setToolTipText("X: " + x + " Y: " + y);
	   }

	   public void mouseExited(MouseEvent e) {
	   }
	}

	
		
}
