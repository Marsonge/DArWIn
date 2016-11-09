package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import controler.WorldControler;
import model.Creature;
import model.grid.Tile;
import utils.UpdateInfoWrapper;

public class ViewGrid extends JPanel implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WorldControler wc;
	private final int TILE_SIZE;
	
	
	/**
	 * 
	 * @param wc
	 */
	public ViewGrid(WorldControler wc){
		super(null);
		this.wc = wc;
		wc.addObserver(this);
		TILE_SIZE = wc.getTileSize();
		int preferredWidth = wc.getSize() * TILE_SIZE;
        int preferredHeight = wc.getSize() * TILE_SIZE;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));

	}
	
	/**
	 * 
	 * @param x : The X coordinate in pixel of the tile
	 * @param y : The Y coordinate in pixel of the tile
	 * @return the colour of the tile at pixel coordinates x,y
	 */
	public Color getTileColor(double x, double y){
		return wc.getTileColour((int) x/TILE_SIZE,(int) y/TILE_SIZE);
	}
	
	@Override
    public void paintComponent(Graphics g) {
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
	
	@Override
	//Is ran everytime WorldControler simulates a tick
	public void update(Observable o, Object arg) {
		UpdateInfoWrapper wrapper = (UpdateInfoWrapper) arg;
		
		// update creatures

		this.removeAll();
		paintCreatures(wrapper.getCreatureList());
		paintTiles(wrapper.getTileList());
		this.revalidate();
		this.repaint();		
	}
	
	private void paintTiles(List<Tile> tileList) {
		int rectWidth = getWidth() / wc.getSize();
        int rectHeight = getHeight() / wc.getSize();
        Graphics g = getGraphics();
		for(Tile t: tileList){
			int x = t.getX() * rectWidth;
            int y = t.getY() * rectHeight;
            Color terrainColor = wc.getTileColour(x,y);
            g.setColor(terrainColor);
            g.fillRect(x, y, rectWidth, rectHeight);
		}
	}

	/**
	 * 
	 * @param arg : The LinkedList that notifyObserver passes through in WorldControler
	 */
	private void paintCreatures(List<Creature> cList){
		for(Creature c : cList){
			ViewCreature vc = new ViewCreature(c,16);
			this.add(vc);
			vc.setVisible(true);
			vc.setSize(16,16);
		}
	}
}
