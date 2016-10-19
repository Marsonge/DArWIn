package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Grid;

public class ViewGrid extends JPanel{
	private Grid grid;
	private final int TILE_SIZE = 10;
	
	public ViewGrid(Grid grid){
		this.grid = grid;
		
		int preferredWidth = grid.getNumCols() * TILE_SIZE;
        int preferredHeight = grid.getNumRows() * TILE_SIZE;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));

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
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Game");
                Grid grid = new Grid(75,75,(float)350);
                ViewGrid vG = new ViewGrid(grid);
                frame.add(vG);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
