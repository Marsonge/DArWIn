package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Grid;

public class ViewGrid extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Grid grid;
	private final int TILE_SIZE = 8;
	
	public ViewGrid(Grid grid){
		this.grid = grid;
		
		int preferredWidth = grid.getNumCols() * TILE_SIZE;
        int preferredHeight = grid.getNumRows() * TILE_SIZE;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));

	}
	
	public Color getTileColor(double d, double e){
		return grid.getTileColour((int) d/TILE_SIZE,(int) e/TILE_SIZE);
	}
	
	@Override
    public void paintComponent(Graphics g) {
        // Important to call super class method
        super.paintComponent(g);
        // Clear the board
        g.clearRect(0, 0, getWidth(), getHeight());
        // Draw the grid
        int rectWidth = getWidth() / grid.getNumCols();
        int rectHeight = getHeight() / grid.getNumRows();

        for (int i = 0; i < grid.getNumRows(); i++) {
            for (int j = 0; j < grid.getNumCols(); j++) {
                // Upper left corner of this terrain rect
                int x = i * rectWidth;
                int y = j * rectHeight;
                Color terrainColor = grid.getTileColour(i,j);
                g.setColor(terrainColor);
                g.fillRect(x, y, rectWidth, rectHeight);
            }
        }
    }
	
	public static void main(String[] args) throws InterruptedException {
		int tick = 1000;
        Grid grid = new Grid(100,(float)10000,0);
        ViewGrid vG = new ViewGrid(grid);
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
