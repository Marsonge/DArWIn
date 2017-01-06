package darwin.darwin.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import darwin.darwin.controler.WorldControler;
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
	private ViewCreature borderCreature;

	/**
	 * Constructor
	 * 
	 * @param wc
	 *            world controler
	 */
	public ViewGrid(WorldControler wc) {
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
		Collection<ViewCreature> lc = wrapper.getCreatureList();
		// checks if there are still creatures on the grid
		if (lc.isEmpty() && !endOfGame) {
			endOfGame = true;
			this.revalidate();
			this.repaint();
			this.fireEndOfGame(new EndOfGameEvent(this));

		} else {
			removeDeadCreatures(wrapper.getDeadList());
			paintCreatures(lc);
			this.revalidate();
			this.repaint();
		}
	}


	private void removeDeadCreatures(List<ViewCreature> deadList) {
		for(ViewCreature vc: deadList){
			this.remove(vc);
		}
	}

	/**
	 * 
	 * @param arg
	 *            : The LinkedList that notifyObserver passes through in
	 *            WorldControler
	 */
	private void paintCreatures(Collection<ViewCreature> cList) {
		for (ViewCreature vc : cList) {
			if(vc.getVG() == null){
				vc.setVG(this);
				this.add(vc);
			}
		}
		
	}
	public void clearBorders(ViewCreature vc){
		if(borderCreature != null){
			borderCreature.setBorder(BorderFactory.createEmptyBorder());
		}
		borderCreature = vc;
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
