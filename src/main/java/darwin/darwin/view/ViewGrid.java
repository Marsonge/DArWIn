package darwin.darwin.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.EventListenerList;

import darwin.darwin.controler.WorldControler;
import darwin.darwin.model.Creature;
import darwin.darwin.model.grid.Tile;
import darwin.darwin.utils.EndOfGameEvent;
import darwin.darwin.utils.EndOfGameEventListener;
import darwin.darwin.utils.UpdateInfoWrapper;

public class ViewGrid extends JPanel implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WorldControler wc;
	private final int TILE_SIZE;
	protected EventListenerList listenerList = new EventListenerList();
	private boolean endOfGame = false;
	private List<ViewCreature> listVc;

	/**
	 * Constructor
	 * 
	 * @param wc
	 *            world controler
	 */
	public ViewGrid(WorldControler wc) {
		super(null);
		this.wc = wc;
		listVc = new LinkedList<>();
		wc.addObserver(this);
		TILE_SIZE = wc.getTileSize();
		int preferredWidth = wc.getSize() * TILE_SIZE;
		int preferredHeight = wc.getSize() * TILE_SIZE;
		setPreferredSize(new Dimension(preferredWidth, preferredHeight));

	}

	/**
	 * 
	 * @param x
	 *            : The X coordinate in pixel of the tile
	 * @param y
	 *            : The Y coordinate in pixel of the tile
	 * @return the colour of the tile at pixel coordinates x,y
	 */
	public Color getTileColor(double x, double y) {
		return wc.getTileColour((int) x / TILE_SIZE, (int) y / TILE_SIZE);
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
				Color terrainColor = wc.getTileColour(i, j);
				g.setColor(terrainColor);
				g.fillRect(x, y, rectWidth, rectHeight);
			}
		}
	}

	@Override
	// Is ran everytime WorldControler simulates a tick
	public void update(Observable o, Object arg) {
		UpdateInfoWrapper wrapper = (UpdateInfoWrapper) arg;

		// update creatures
		this.removeAll();
		List<Creature> lc = wrapper.getCreatureList();
		// checks if there are still creatures on the grid
		if (lc.isEmpty() && !endOfGame) {
			endOfGame = true;
			this.revalidate();
			this.repaint();
			this.fireEndOfGame(new EndOfGameEvent(this));

		} else {
			paintCreatures(lc);
			paintTiles(wrapper.getTileList());
			this.revalidate();
			this.repaint();
		}
	}

	private void paintTiles(List<Tile> tileList) {
		int rectWidth = getWidth() / wc.getSize();
		int rectHeight = getHeight() / wc.getSize();
		Graphics g = getGraphics();
		for (Tile t : tileList) {
			int x = t.getX() * rectWidth;
			int y = t.getY() * rectHeight;
			Color terrainColor = wc.getTileColour(x, y);
			g.setColor(terrainColor);
			g.fillRect(x, y, rectWidth, rectHeight);
		}
	}

	/**
	 * 
	 * @param arg
	 *            : The LinkedList that notifyObserver passes through in
	 *            WorldControler
	 */
	private void paintCreatures(List<Creature> cList) {
		listVc.clear();
		for (Creature c : cList) {

			ViewCreature vc = new ViewCreature(16, c.getX(), c.getY(), c.getSpeed(), this.wc, this);
			this.add(vc);
			if (c.equals(this.wc.getCurrentCreature())) {
				Border border = new LineBorder(Color.RED, 3, true);
				vc.setBorder(border);
			}
			vc.setVisible(true);
			vc.setSize(16, 16);
			listVc.add(vc);
		}
	}
	
	public void clearBorders(){
		
		for(ViewCreature vc :listVc){
			vc.setBorder(BorderFactory.createEmptyBorder());
		}
	}

	/**
	 * Event management methods
	 */

	public void addEndOfGameListener(EndOfGameEventListener listener) {
		listenerList.add(EndOfGameEventListener.class, listener);
	}

	public void removeEndOfGameListener(EndOfGameEventListener listener) {
		listenerList.remove(EndOfGameEventListener.class, listener);
	}

	void fireEndOfGame(EndOfGameEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == EndOfGameEventListener.class) {
				((EndOfGameEventListener) listeners[i + 1]).actionPerformed(evt);
			}
		}
	}
}
