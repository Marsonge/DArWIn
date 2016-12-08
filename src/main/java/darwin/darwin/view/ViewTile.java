package darwin.darwin.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class ViewTile extends JComponent{
	
	private static final long serialVersionUID = -4020756606710478263L;
	private Color color;
	private int x;
	private int y;
	private int rectWidth;
	private int rectHeight;
	
	public ViewTile(Color color, int x, int y, int width, int height){
		this.color = color;
		this.x = x;
		this.y = y;
		this.rectWidth = width;
		this.rectHeight = height;
	}

	@Override
	public void paintComponent(Graphics g){
		g.setColor(this.color);
        g.fillRect(this.x, this.y, this.rectWidth, this.rectHeight);
	}
}
