package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import controler.WorldControler;
import model.Creature;
import model.grid.Grid;
import model.grid.Tile;
import utils.UpdateInfoWrapper;

public class ViewGrid extends JPanel implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WorldControler wc;
	private final int TILE_SIZE;
	private int tick;
	private final Timer timer;
	
	
	/**
	 * 
	 * @param wc
	 */
	public ViewGrid(WorldControler wc){
		super(null);
		this.wc = wc;
		wc.addObserver(this);
		TILE_SIZE = wc.getTileSize();
		this.tick = 1000; //Is in milliseconds
		int preferredWidth = wc.getSize() * TILE_SIZE;
        int preferredHeight = wc.getSize() * TILE_SIZE;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        this.timer = new Timer(tick, new TimerActionListener(wc));

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
		this.revalidate();
		this.repaint();
		// updates eaten tiles
		for(Tile t : wrapper.getTileList()){
			//TODO update tiles : repaint revalidate
		}
		
	}
	
	/**
	 * 
	 * @param arg : The LinkedList that notifyObserver passes through in WorldControler
	 */
	private void paintCreatures(Object arg){
		LinkedList<Creature> cList = (LinkedList<Creature>) arg;
		for(Creature c : cList){
			ViewCreature vc = new ViewCreature(c);
			this.add(vc);
			vc.setVisible(true);
			vc.setSize(32, 32);
			System.out.println(vc.getX() + " " + vc.getY());
		}
	}
}
