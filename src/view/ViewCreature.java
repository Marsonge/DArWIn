package view;

import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import model.Creature;

public class ViewCreature extends JLabel {

	private static final long serialVersionUID = 3003074446528565112L;
	private Creature c;
	
	public ViewCreature(Creature c){
		super();
		this.c = c;
		ImageIcon img = new ImageIcon("resources/img/creature.png");
		this.setIcon(img);
		this.setLocation(c.getX(), c.getY());
		this.setSize(5,5);
	}

	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		c.setX(x);
		c.setY(y);
	}

	@Override
	public void setLocation(Point p) {
		super.setLocation(p);
		c.setX((int)p.getX());
		c.setY((int)p.getY());
	}
	
	
}
