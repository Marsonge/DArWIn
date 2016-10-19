package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import controler.WorldControler;
import model.Grid;

public class ViewGrid extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WorldControler wc;
	private final int TILE_SIZE = 8;
	private int tick;
	private final Timer timer;
	
	public ViewGrid(WorldControler wc){
		this.wc = wc;
		
		this.tick = 1000;
		int preferredWidth = wc.getSize() * TILE_SIZE;
        int preferredHeight = wc.getSize() * TILE_SIZE;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        this.timer = new Timer(tick, new TimerActionListener(wc));

	}
	
	public Color getTileColor(double d, double e){
		return wc.getTileColour((int) d/TILE_SIZE,(int) e/TILE_SIZE);
	}
	
	@Override
    public void paintComponent(Graphics g) {
        // Important to call super class method
        super.paintComponent(g);
        // Clear the board
        g.clearRect(0, 0, getWidth(), getHeight());
        // Draw the grid
        int rectWidth = getWidth() / wc.getSize();
        int rectHeight = getHeight() / wc.getSize();

        for (int i = 0; i < wc.getSize(); i++) {
            for (int j = 0; j < wc.getSize(); j++) {
                // Upper left corner of this terrain rect
                int x = i * rectWidth;
                int y = j * rectHeight;
                Color terrainColor = wc.getTileColour(i,j);
                g.setColor(terrainColor);
                g.fillRect(x, y, rectWidth, rectHeight);
            }
        }
    }
	
	public static void main(String[] args) throws InterruptedException {
		int tick = 1000;
        WorldControler wc = new WorldControler(100,(float)10000,0,60);
        ViewGrid vG = new ViewGrid(wc);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Game");
                frame.add(vG);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        //Thread.sleep(tick);
        //while(true){
         //   Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
          //  Color c = vG.getTileColor(mouseLoc.getX() - vG.getLocationOnScreen().getX(),(mouseLoc.getY() - vG.getLocationOnScreen().getY()));
        //    System.out.println(c.getRed() + " " + c.getGreen() + " " + c.getBlue());
        //    Thread.sleep(tick);
       // }
    }
}
