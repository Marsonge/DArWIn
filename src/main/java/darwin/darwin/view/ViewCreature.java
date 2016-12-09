package darwin.darwin.view;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import darwin.darwin.controler.WorldControler;
import darwin.darwin.utils.Utils;

public class ViewCreature extends JLabel {

	private static final long serialVersionUID = 3003074446528565112L;
	private Image img;
	private int size;
	private WorldControler wc;
	private int x;
	private int y;
	private ViewCreature self = this;
	private static URL[] IMAGESURL = {
			Utils.getResource("img/creature0.png"),
			Utils.getResource("img/creature1.png"),
			Utils.getResource("img/creature2.png"),
			Utils.getResource("img/creature3.png"),
			Utils.getResource("img/creature4.png"),
			Utils.getResource("img/creature5.png"),
			Utils.getResource("img/creature6.png"),
			Utils.getResource("img/creature7.png")
	};
	
	private static ArrayList<BufferedImage> IMAGES= null;
	
	public ViewCreature(int size, int x, int y, float speed, WorldControler wc){
		super();
		this.size = size;
		this.setLocation(x,y);
		this.x = x;
		this.y = y;
		this.wc = wc;
		this.setSize(size,size);
		/* Getting all the images only once
		 * Because ImageIO.read(URL) is so slow
		 */
		if(IMAGES==null){
			IMAGES = new ArrayList<>();
			try {
				for(int i=0; i<8; i++){
					IMAGES.add(ImageIO.read(IMAGESURL[i]));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.img = IMAGES.get(Math.round(speed)).getScaledInstance(size,size,Image.SCALE_SMOOTH); //Resizes the image. Try to keep size a power of 2!
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
		   wc.setCurrentCreature(x, y);
		   System.out.println("current creature : " + wc.getCurrentCreature().getX() + " " + wc.getCurrentCreature().getY());
	   }

	   public void mousePressed(MouseEvent e) {
	   }

	   public void mouseReleased(MouseEvent e) {
	   }

	   public void mouseEntered(MouseEvent e) {
		   // TODO opti ?
		   self.setToolTipText("Energy: " + wc.getCreatureEnergy(x,y) + " Speed: " + wc.getCreatureSpeed(x,y));
	   }

	   public void mouseExited(MouseEvent e) {
	   }
	}

	
		
}
