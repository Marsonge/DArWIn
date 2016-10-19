package view;

import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import model.Creature;

public class ViewCreature extends JLabel {
	private Creature c;
	
	public ViewCreature(Creature c){
		this.c = c;
		ImageIcon img = new ImageIcon("img/creature.png");
		this.setIcon(img);
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
