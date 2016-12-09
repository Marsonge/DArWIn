package darwin.darwin.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import darwin.darwin.utils.Tool;


public class ViewMapGrid extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int width = 129;
	private final int height = 129;
	private double zoomLevel = 1;
	private ViewMapGrid self = this;
	private Color grid[][];
	static int TILESIZE = 1;

	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Clear the board
        g.clearRect(0, 0, getWidth(), getHeight());
        // Draw the grid

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height ; j++) {
                // Upper left corner of this terrain rect
                int x = i * TILESIZE;
                int y = j * TILESIZE;
                Color terrainColor = grid[i][j];
                g.setColor(terrainColor);
                g.fillRect(x, y, TILESIZE, TILESIZE);
            }
        }
    }
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension((int)(width * zoomLevel),(int) (height * zoomLevel));
	}
	
	public ViewMapGrid(ViewMapEditor parent){
		super();
		this.grid = new Color[width][height];
		for (int i = 0; i < width; i++) {
            for (int j = 0; j < height ; j++) {
                grid[i][j] = Color.BLACK;
            }
		}
		this.setPreferredSize(new Dimension((int)(width*zoomLevel),(int)(height*zoomLevel)));
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if(parent.getTool().equals(Tool.BRUSH)){
					Color c = parent.getCurrentColor();
					paintTile(e.getX(),e.getY(),c);
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		this.addMouseListener(new MouseListener() {
			
			int originX;
			int originY;
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(parent.getTool().equals(Tool.RECTANGLE)){
					self.paintRectangle(originX,originY,e.getX(),e.getY(),parent.getCurrentColor());
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(parent.getTool().equals(Tool.RECTANGLE)){
					originX = e.getX();
					originY = e.getY();
				}
				if(parent.getTool().equals(Tool.BRUSH)){
					Color c = parent.getCurrentColor();
					paintTile(e.getX(),e.getY(),c);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void paintRectangle(int originX, int originY, int x, int y, Color currentColor) {
		int properOX = originX/TILESIZE;
		int properOY = originY/TILESIZE;
		int properX = x/TILESIZE;
		int properY = y/TILESIZE;
		if(properX > 129){
			properX = 129;
		}if(properX < 0){
			properX = 0;
		}if(properY > 129){
			properY = 129;
		}if(properY < 0){
			properY = 0;
		}
		if(properOX > 129){
			properOX = 129;
		}if(properOX < 0){
			properOX = 0;
		}if(properOY > 129){
			properOY = 129;
		}if(properOY < 0){
			properOY = 0;
		}
		int startX = (properX < properOX) ? properX : properOX;
		int endX = (properX > properOX) ? properX : properOX;
		int startY = (properY < properOY) ? properY : properOY;
		int endY = (properY > properOY) ? properY : properOY;
		
		for(int i = startX;i < endX ;i++){
			for(int j=startY;j<endY;j++){
				grid[i][j] = currentColor;
			}
		}
		this.repaint();
		
	}

	public void paintTile(int x, int y, Color c) {
		int properX = x/TILESIZE;
		int properY = y/TILESIZE;
		if(properX > 129 || properY > 129 || properX < 0 || properY < 0){
			return;
		}
		grid[properX][properY] = c;
		this.repaint();
	}
	public void zoom() {
		TILESIZE++;
		if(TILESIZE>=5){
			TILESIZE = 5;
		}
	}
	public void unzoom() {
		TILESIZE--;
		if(TILESIZE<=1){
			TILESIZE = 1;
		}
	}
}
