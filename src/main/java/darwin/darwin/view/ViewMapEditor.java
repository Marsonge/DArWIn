package darwin.darwin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import darwin.darwin.controler.WorldControler;
import darwin.darwin.model.grid.Grid;
import darwin.darwin.utils.Tool;


public class ViewMapEditor extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ViewMapGrid grid;
	private final ViewMapEditor self = this;
	private final ViewPalette palette = new ViewPalette(this);
	private final WorldControler parent;
	
	public ViewMapEditor(){
		this((Grid)null,null);
	}
	
	public ViewMapEditor(Grid tiles,WorldControler parent){
		
		// Create the main JFrame
		this.setTitle("Darwin Map Editor");
		if(tiles==null){
			grid = new ViewMapGrid(this,null);
		}
		else{
			grid = new ViewMapGrid(this,tiles);
		}
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(); 
        int height = gd.getDisplayMode().getHeight();
        if(height < 900){
    		this.setPreferredSize(new Dimension(550,650));
    		grid.setZoomLevel(4);
        }
		this.setPreferredSize(new Dimension(930,810));
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.parent = parent;
		
        // main JFrame setting
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);     
		this.setExtendedState(JFrame.NORMAL);
		this.add(grid,BorderLayout.CENTER);
		this.add(palette,BorderLayout.EAST);
		this.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation() < 0){
					grid.zoom();
				}
				else{
					grid.unzoom();
				}
				self.pack();
				self.repaint();
			}
		});
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}	
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z){
					self.undo();
				}
			}
		});
		this.repaint();
		this.pack();
		this.setVisible(true);
		this.requestFocus();

		
		
	}
	


	public Color getCurrentColor() {
		this.requestFocus();
		return palette.getCurrentColor();
	}



	public Tool getTool() {
		this.requestFocus();
		return palette.getTool();
	}

	public void save() {
		parent.endMapEdition(createGridFromMap(grid.getGridSize(),grid.getTiles()));
		this.dispose();
	}

	private Grid createGridFromMap(int size,Color[][] tiles) {
		Grid g = new Grid(size,tiles);
		return g;
	}

	public void undo() {
		this.requestFocus();
		grid.undo();
	}


}
