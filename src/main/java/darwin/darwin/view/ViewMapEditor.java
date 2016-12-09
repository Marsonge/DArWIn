package darwin.darwin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import darwin.darwin.utils.Tool;


public class ViewMapEditor extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ViewMapGrid grid;
	private final ViewMapEditor self = this;
	private final ViewPalette palette = new ViewPalette(this);
	
	public ViewMapEditor(){
		
		// Create the main JFrame
		this.setTitle("Darwin : ARtificial Wildlife INtelligence");
		this.setPreferredSize(new Dimension(900,700));
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		grid = new ViewMapGrid(this);
        // main JFrame setting
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
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
		this.repaint();
		this.pack();
		this.setVisible(true);
		
	}
	
	
	
	public static void main(String[] args){
		ViewMapEditor vm = new ViewMapEditor();
	}



	public Color getCurrentColor() {
		return palette.getCurrentColor();
	}



	public Tool getTool() {
		return palette.getTool();
	}
}
