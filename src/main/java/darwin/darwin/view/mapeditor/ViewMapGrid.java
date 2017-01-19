package darwin.darwin.view.mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import darwin.darwin.model.grid.Grid;
import darwin.darwin.model.grid.Terrain;
import darwin.darwin.utils.Tool;
import darwin.darwin.utils.Utils;


public class ViewMapGrid extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int width = 129;
	private final int height = 129;
	private ViewMapGrid self = this;
	private Color grid[][];
	private Color previousGrids[][][];
	static int TILESIZE = 6;
	private Rectangle preview;
	private ViewMapEditor parent;
	private int originX,originY;
	private int currentUndoLevel = 0;
	private int successiveUndos = 0;
	private int numberOfPossibleUndos = 0;
	
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
        if(preview != null){
        	g.setColor(parent.getCurrentColor());
        	g.fillRect((int)preview.getX(), (int)preview.getY(), (int)preview.getWidth(), (int)preview.getHeight());
        }
    }
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension((int)(width * TILESIZE),(int) (height * TILESIZE));
	}
	

	public ViewMapGrid(ViewMapEditor parent, Grid tiles) {
		super();
		this.parent = parent;
		this.grid = new Color[width][height];
		for(int i = 0;i<width;i++){
			for(int j = 0;j<height;j++){
				grid[i][j] = tiles.getTileColour(i, j);
			}
		}
		this.previousGrids = new Color[width][height][5];
		this.setPreferredSize(new Dimension((int)(width * TILESIZE),(int)(height * TILESIZE)));
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if(parent.getTool().equals(Tool.BRUSH)){
					Color c = parent.getCurrentColor();
					paintTile(e.getX(),e.getY(),c);
				}
				if(parent.getTool().equals(Tool.RECTANGLE)){
					int x = originX;
					int y = originY;
					int curX = e.getX();
					int curY = e.getY();
					int width,height;
					if(x>=curX){
						width = x-curX;
						x = curX;
					}
					else{
						width = curX - x;
					}
					if(y>=curY){
						height = y-curY;
						y = curY;
					}
					else{
						height = curY - y;
					}
					preview.setBounds(x,y,width,height);
					self.repaint();
				}
				if(parent.getTool().equals(Tool.LARGEBRUSH)){
					Color c = parent.getCurrentColor();
					paintTile(e.getX(),e.getY(),c);

					paintTile(e.getX()+(1*TILESIZE),e.getY(),c);
					paintTile(e.getX()+(2*TILESIZE),e.getY(),c);
					
					paintTile(e.getX()+(1*TILESIZE),e.getY()+(1*TILESIZE),c);
					paintTile(e.getX()+(2*TILESIZE),e.getY()+(1*TILESIZE),c);
					paintTile(e.getX()+(1*TILESIZE),e.getY()+(2*TILESIZE),c);
					paintTile(e.getX()+(2*TILESIZE),e.getY()+(2*TILESIZE),c);
					
					paintTile(e.getX()+(1*TILESIZE),e.getY()-(1*TILESIZE),c);
					paintTile(e.getX()+(2*TILESIZE),e.getY()-(1*TILESIZE),c);
					paintTile(e.getX()+(1*TILESIZE),e.getY()-(2*TILESIZE),c);
					paintTile(e.getX()+(2*TILESIZE),e.getY()-(2*TILESIZE),c);
					
					paintTile(e.getX()-(1*TILESIZE),e.getY(),c);
					paintTile(e.getX()-(2*TILESIZE),e.getY(),c);
					
					paintTile(e.getX()-(1*TILESIZE),e.getY()+(1*TILESIZE),c);
					paintTile(e.getX()-(2*TILESIZE),e.getY()+(1*TILESIZE),c);
					paintTile(e.getX()-(1*TILESIZE),e.getY()+(2*TILESIZE),c);
					paintTile(e.getX()-(2*TILESIZE),e.getY()+(2*TILESIZE),c);
					
					paintTile(e.getX()-(1*TILESIZE),e.getY()-(1*TILESIZE),c);
					paintTile(e.getX()-(2*TILESIZE),e.getY()-(1*TILESIZE),c);
					paintTile(e.getX()-(1*TILESIZE),e.getY()-(2*TILESIZE),c);
					paintTile(e.getX()-(2*TILESIZE),e.getY()-(2*TILESIZE),c);
					
					paintTile(e.getX(),e.getY()+(1*TILESIZE),c);
					paintTile(e.getX(),e.getY()+(2*TILESIZE),c);
					paintTile(e.getX(),e.getY()-(1*TILESIZE),c);
					paintTile(e.getX(),e.getY()-(2*TILESIZE),c);
					
					paintTile(e.getX(),e.getY()+(3*TILESIZE),c);
					paintTile(e.getX()-(1*TILESIZE),e.getY()+(3*TILESIZE),c);
					paintTile(e.getX()+(1*TILESIZE),e.getY()+(3*TILESIZE),c);
					paintTile(e.getX(),e.getY()-(3*TILESIZE),c);
					paintTile(e.getX()-(1*TILESIZE),e.getY()-(3*TILESIZE),c);
					paintTile(e.getX()+(1*TILESIZE),e.getY()-(3*TILESIZE),c);
					
					paintTile(e.getX()+(3*TILESIZE),e.getY(),c);
					paintTile(e.getX()+(3*TILESIZE),e.getY()-(1*TILESIZE),c);
					paintTile(e.getX()+(3*TILESIZE),e.getY()+(1*TILESIZE),c);
					paintTile(e.getX()-(3*TILESIZE),e.getY(),c);
					paintTile(e.getX()-(3*TILESIZE),e.getY()-(1*TILESIZE),c);
					paintTile(e.getX()-(3*TILESIZE),e.getY()+(1*TILESIZE),c);
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		this.addMouseListener(new MouseListener() {
						
			@Override
			public void mouseReleased(MouseEvent e) {
				if(parent.getTool().equals(Tool.RECTANGLE)){
					self.paintRectangle(originX,originY,e.getX(),e.getY(),parent.getCurrentColor());
					preview = null;
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				self.previousGrids[currentUndoLevel] = (Color[][]) Utils.deepCopyColorMatrix(self.grid);
				currentUndoLevel = (currentUndoLevel + 1)%5;
				successiveUndos = 0;
				numberOfPossibleUndos++;
				if(parent.getTool().equals(Tool.RECTANGLE)){
					originX = e.getX();
					originY = e.getY();
					preview = new Rectangle(originX,originY,0,0);
				}
				if(parent.getTool().equals(Tool.BRUSH)){
					Color c = parent.getCurrentColor();
					paintTile(e.getX(),e.getY(),c);
				}
				if(parent.getTool().equals(Tool.FILLBUCKET)){
					Color c = parent.getCurrentColor();
					fillBucket(e.getX(),e.getY(),c,self.getTileColor(e.getX(),e.getY()));
				}
				if(parent.getTool().equals(Tool.LARGEBRUSH)){
					Color c = parent.getCurrentColor();
					paintTile(e.getX(),e.getY(),c);

					paintTile(e.getX()+(1*TILESIZE),e.getY(),c);
					paintTile(e.getX()+(2*TILESIZE),e.getY(),c);
					
					paintTile(e.getX()+(1*TILESIZE),e.getY()+(1*TILESIZE),c);
					paintTile(e.getX()+(2*TILESIZE),e.getY()+(1*TILESIZE),c);
					paintTile(e.getX()+(1*TILESIZE),e.getY()+(2*TILESIZE),c);
					paintTile(e.getX()+(2*TILESIZE),e.getY()+(2*TILESIZE),c);
					
					paintTile(e.getX()+(1*TILESIZE),e.getY()-(1*TILESIZE),c);
					paintTile(e.getX()+(2*TILESIZE),e.getY()-(1*TILESIZE),c);
					paintTile(e.getX()+(1*TILESIZE),e.getY()-(2*TILESIZE),c);
					paintTile(e.getX()+(2*TILESIZE),e.getY()-(2*TILESIZE),c);
					
					paintTile(e.getX()-(1*TILESIZE),e.getY(),c);
					paintTile(e.getX()-(2*TILESIZE),e.getY(),c);
					
					paintTile(e.getX()-(1*TILESIZE),e.getY()+(1*TILESIZE),c);
					paintTile(e.getX()-(2*TILESIZE),e.getY()+(1*TILESIZE),c);
					paintTile(e.getX()-(1*TILESIZE),e.getY()+(2*TILESIZE),c);
					paintTile(e.getX()-(2*TILESIZE),e.getY()+(2*TILESIZE),c);
					
					paintTile(e.getX()-(1*TILESIZE),e.getY()-(1*TILESIZE),c);
					paintTile(e.getX()-(2*TILESIZE),e.getY()-(1*TILESIZE),c);
					paintTile(e.getX()-(1*TILESIZE),e.getY()-(2*TILESIZE),c);
					paintTile(e.getX()-(2*TILESIZE),e.getY()-(2*TILESIZE),c);
					
					paintTile(e.getX(),e.getY()+(1*TILESIZE),c);
					paintTile(e.getX(),e.getY()+(2*TILESIZE),c);
					paintTile(e.getX(),e.getY()-(1*TILESIZE),c);
					paintTile(e.getX(),e.getY()-(2*TILESIZE),c);
					
					paintTile(e.getX(),e.getY()+(3*TILESIZE),c);
					paintTile(e.getX()-(1*TILESIZE),e.getY()+(3*TILESIZE),c);
					paintTile(e.getX()+(1*TILESIZE),e.getY()+(3*TILESIZE),c);
					paintTile(e.getX(),e.getY()-(3*TILESIZE),c);
					paintTile(e.getX()-(1*TILESIZE),e.getY()-(3*TILESIZE),c);
					paintTile(e.getX()+(1*TILESIZE),e.getY()-(3*TILESIZE),c);
					
					paintTile(e.getX()+(3*TILESIZE),e.getY(),c);
					paintTile(e.getX()+(3*TILESIZE),e.getY()-(1*TILESIZE),c);
					paintTile(e.getX()+(3*TILESIZE),e.getY()+(1*TILESIZE),c);
					paintTile(e.getX()-(3*TILESIZE),e.getY(),c);
					paintTile(e.getX()-(3*TILESIZE),e.getY()-(1*TILESIZE),c);
					paintTile(e.getX()-(3*TILESIZE),e.getY()+(1*TILESIZE),c);
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
	

	protected void fillBucket(int x, int y, Color target, Color toReplace) {
		if(isSameTile(target,toReplace)){
			return;
		}
		if(!(isSameTile(getTileColor(x,y),toReplace))){
			return;
		}
		grid[x/TILESIZE][y/TILESIZE] = target;
		if((x+TILESIZE)/TILESIZE < width)
			fillBucket(x+TILESIZE, y, target, toReplace);
		if((x-TILESIZE)/TILESIZE >= 0)
			fillBucket(x-TILESIZE, y, target, toReplace);
		
		if((y+TILESIZE)/TILESIZE < height)
			fillBucket(x, y+TILESIZE, target, toReplace);
		if((y-TILESIZE)/TILESIZE >= 0)
			fillBucket(x, y-TILESIZE, target, toReplace);
		
		this.repaint();

	}

	private boolean isSameTile(Color tileColor, Color toReplace) {
		if(tileColor.getRGB() == Terrain.MOUNTAINS.getRGB() || 
		  (tileColor.getBlue() == Terrain.MOUNTAINS.getBlueValue() 
		  && tileColor.getRed() == Terrain.MOUNTAINS.getRedValue() 
		  && tileColor.getGreen() <= 135 && tileColor.getGreen() >= 110)){
			return ((toReplace.getBlue() == Terrain.MOUNTAINS.getBlueValue() 
					&& toReplace.getRed() == Terrain.MOUNTAINS.getRedValue() 
					&& toReplace.getGreen() <= 135 && toReplace.getGreen() >= 110));
		}
		else if(tileColor.getRGB() == Terrain.WOODS.getRGB() || 
			   (tileColor.getBlue() == Terrain.WOODS.getBlueValue() 
			   && tileColor.getRed() == Terrain.WOODS.getRedValue() 
			   && tileColor.getGreen() <= 185 && tileColor.getGreen() >= 110)){
			return ((toReplace.getBlue() == Terrain.WOODS.getBlueValue() 
					   && toReplace.getRed() == Terrain.WOODS.getRedValue() 
					   && toReplace.getGreen() <= 185 && toReplace.getGreen() >= 110));
		}
		return tileColor.equals(toReplace);
	}

	protected Color getTileColor(int x, int y) {
		return grid[(int) Math.floor(x/TILESIZE)][(int) Math.floor(y/TILESIZE)];
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
		try{
			grid[properX][properY] = c;
		}
		catch(ArrayIndexOutOfBoundsException e){
			
		}
		this.repaint();
	}
	public void zoom() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(); 
        int height = gd.getDisplayMode().getHeight();
        int maxZ = 6;
        if(height<900)maxZ=4;
		TILESIZE++;
		if(TILESIZE>=maxZ){
			TILESIZE = maxZ;
		}
	}
	public void unzoom() {
		TILESIZE--;
		if(TILESIZE<=1){
			TILESIZE = 1;
		}
	}
	public void setZoomLevel(int z){
		TILESIZE = z;
	}

	public Color[][] getTiles() {
		return grid;
	}
	
	public int getGridSize(){
		return width;
	}
	
	public void undo(){
		if(successiveUndos>=5 || numberOfPossibleUndos==0)
			return;
		successiveUndos++;
		numberOfPossibleUndos--;
		int index = (currentUndoLevel - successiveUndos)%5;
		if(index<0) index+=5;
		grid = Utils.deepCopyColorMatrix(previousGrids[index]);
		this.repaint();
	}
}
