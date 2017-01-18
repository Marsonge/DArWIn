package darwin.darwin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import darwin.darwin.model.grid.Terrain;
import darwin.darwin.utils.Tool;
import darwin.darwin.utils.Utils;

public class ViewPalette extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2195288634007240391L;
    private Terrain t = Terrain.values()[0];
    private final JButton etherButton = new JButton("Ether");
    private final JButton deepButton = new JButton("Deep Ocean");
    private final JButton oceanButton = new JButton("Ocean");
    private final JButton shallowButton = new JButton("Shallow sea");
    private final JButton sandButton = new JButton("Sand");
    private final JButton woodButton = new JButton("Woods");
    private final JButton mountainsButton = new JButton("Mountains");
    private final JButton snowButton = new JButton("Snow");
    private final JButton undoButton = new JButton("Undo");
    
    private Terrain current;

    private final JComboBox<Tool> toolBox;
    private final JPanel toolPanel = new JPanel();
    private final JButton brush = new JButton();
    private final JButton largeBrush = new JButton();
    private final JButton rectangle = new JButton();
    private final JButton fillBucket = new JButton();
   
    private final JButton saveButton = new JButton("Save");
    private final JButton quitButton = new JButton("Quit");
    private final JPanel preview = new JPanel();
    
	public ViewPalette(ViewMapEditor parent){
		super();
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		toolBox = new JComboBox<Tool>();
		preview.setBackground(new Color(t.getRGB()));
		
		toolPanel.setLayout(new FlowLayout());
		toolPanel.setMaximumSize(new Dimension(200,60));
		 try {
			 brush.setIcon(new ImageIcon(ImageIO.read(Utils.getResource("img/tools/pencil.png"))));
			 brush.setBackground(Color.GRAY); // selected by default
			 brush.setPreferredSize(new Dimension(40,50));
			 
			 largeBrush.setIcon(new ImageIcon(ImageIO.read(Utils.getResource("img/tools/brush.png"))));
			 largeBrush.setBackground(Color.lightGray); // selected by default
			 largeBrush.setPreferredSize(new Dimension(40,50));
			 
			 rectangle.setBackground(Color.lightGray);
			 rectangle.setIcon(new ImageIcon(ImageIO.read(Utils.getResource("img/tools/rectangle.png"))));
			 rectangle.setPreferredSize(new Dimension(40,50));
			 
			 fillBucket.setBackground(Color.lightGray);
			 fillBucket.setIcon(new ImageIcon(ImageIO.read(Utils.getResource("img/tools/fillbucket.png"))));
			 fillBucket.setPreferredSize(new Dimension(40,50));
			 
			 toolPanel.add(brush);
			 toolPanel.add(largeBrush);
			 toolPanel.add(rectangle);
			 toolPanel.add(fillBucket);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        for(Tool t : Tool.values()){
        	toolBox.addItem(t);
        }
        
        brush.addActionListener(e -> {
        	brush.setBackground(Color.GRAY);
        	largeBrush.setBackground(Color.lightGray);
        	rectangle.setBackground(Color.lightGray);
        	fillBucket.setBackground(Color.lightGray);
        	toolBox.setSelectedItem(Tool.BRUSH);
        });
        
        largeBrush.addActionListener(e -> {
        	brush.setBackground(Color.lightGray);
        	largeBrush.setBackground(Color.GRAY);
        	rectangle.setBackground(Color.lightGray);
        	fillBucket.setBackground(Color.lightGray);
        	toolBox.setSelectedItem(Tool.LARGEBRUSH);
        });
        
        rectangle.addActionListener(e -> {
        	brush.setBackground(Color.lightGray);
        	largeBrush.setBackground(Color.lightGray);
        	rectangle.setBackground(Color.GRAY);
        	fillBucket.setBackground(Color.lightGray);
        	toolBox.setSelectedItem(Tool.RECTANGLE);
        });
        
        fillBucket.addActionListener(e -> {
        	brush.setBackground(Color.lightGray);
        	largeBrush.setBackground(Color.lightGray);
        	rectangle.setBackground(Color.lightGray);
        	fillBucket.setBackground(Color.GRAY);
        	toolBox.setSelectedItem(Tool.FILLBUCKET);
        });
        
        current = Terrain.ETHER;
        etherButton.setBackground(Color.GRAY);
    	deepButton.setBackground(Color.lightGray);
    	oceanButton.setBackground(Color.lightGray);
    	shallowButton.setBackground(Color.lightGray);
    	sandButton.setBackground(Color.lightGray);
    	woodButton.setBackground(Color.lightGray);
    	mountainsButton.setBackground(Color.lightGray);
    	snowButton.setBackground(Color.lightGray);
        saveButton.addActionListener(e -> {
        	parent.save();
        });
        quitButton.addActionListener(e-> {
        	parent.dispose();
        });
        etherButton.addActionListener(e -> {
        	current = Terrain.ETHER;
        	etherButton.setBackground(Color.GRAY);
        	deepButton.setBackground(Color.lightGray);
        	oceanButton.setBackground(Color.lightGray);
        	shallowButton.setBackground(Color.lightGray);
        	sandButton.setBackground(Color.lightGray);
        	woodButton.setBackground(Color.lightGray);
        	mountainsButton.setBackground(Color.lightGray);
        	snowButton.setBackground(Color.lightGray);
        	preview.setBackground(getCurrentColor());
        });
        deepButton.addActionListener(e -> {
        	current = Terrain.DEEP_WATER;
        	etherButton.setBackground(Color.lightGray);
        	deepButton.setBackground(Color.GRAY);
        	oceanButton.setBackground(Color.lightGray);
        	shallowButton.setBackground(Color.lightGray);
        	sandButton.setBackground(Color.lightGray);
        	woodButton.setBackground(Color.lightGray);
        	mountainsButton.setBackground(Color.lightGray);
        	snowButton.setBackground(Color.lightGray);
        	preview.setBackground(getCurrentColor());
        });
        oceanButton.addActionListener(e -> {
        	current = Terrain.OCEAN;
        	etherButton.setBackground(Color.lightGray);
        	deepButton.setBackground(Color.lightGray);
        	oceanButton.setBackground(Color.GRAY);
        	shallowButton.setBackground(Color.lightGray);
        	sandButton.setBackground(Color.lightGray);
        	woodButton.setBackground(Color.lightGray);
        	mountainsButton.setBackground(Color.lightGray);
        	snowButton.setBackground(Color.lightGray);
        	preview.setBackground(getCurrentColor());
        });
        shallowButton.addActionListener(e -> {
        	current = Terrain.SHALLOW_WATER;
        	etherButton.setBackground(Color.lightGray);
        	deepButton.setBackground(Color.lightGray);
        	oceanButton.setBackground(Color.lightGray);
        	shallowButton.setBackground(Color.GRAY);
        	sandButton.setBackground(Color.lightGray);
        	woodButton.setBackground(Color.lightGray);
        	mountainsButton.setBackground(Color.lightGray);
        	snowButton.setBackground(Color.lightGray);
        	preview.setBackground(getCurrentColor());
        });
        sandButton.addActionListener(e -> {
        	current = Terrain.SAND;
        	etherButton.setBackground(Color.lightGray);
        	deepButton.setBackground(Color.lightGray);
        	oceanButton.setBackground(Color.lightGray);
        	shallowButton.setBackground(Color.lightGray);
        	sandButton.setBackground(Color.GRAY);
        	woodButton.setBackground(Color.lightGray);
        	mountainsButton.setBackground(Color.lightGray);
        	snowButton.setBackground(Color.lightGray);
        	preview.setBackground(getCurrentColor());
        });
        woodButton.addActionListener(e -> {
        	current = Terrain.WOODS;
        	etherButton.setBackground(Color.lightGray);
        	deepButton.setBackground(Color.lightGray);
        	oceanButton.setBackground(Color.lightGray);
        	shallowButton.setBackground(Color.lightGray);
        	sandButton.setBackground(Color.lightGray);
        	woodButton.setBackground(Color.GRAY);
        	mountainsButton.setBackground(Color.lightGray);
        	snowButton.setBackground(Color.lightGray);
        	preview.setBackground(getCurrentColor());
        });
        mountainsButton.addActionListener(e -> {
        	current = Terrain.MOUNTAINS;
        	etherButton.setBackground(Color.lightGray);
        	deepButton.setBackground(Color.lightGray);
        	oceanButton.setBackground(Color.lightGray);
        	shallowButton.setBackground(Color.lightGray);
        	sandButton.setBackground(Color.lightGray);
        	woodButton.setBackground(Color.lightGray);
        	mountainsButton.setBackground(Color.GRAY);
        	snowButton.setBackground(Color.lightGray);
        	preview.setBackground(getCurrentColor());
        });
        snowButton.addActionListener(e -> {
        	current = Terrain.SNOW;
        	etherButton.setBackground(Color.lightGray);
        	deepButton.setBackground(Color.lightGray);
        	oceanButton.setBackground(Color.lightGray);
        	shallowButton.setBackground(Color.lightGray);
        	sandButton.setBackground(Color.lightGray);
        	woodButton.setBackground(Color.lightGray);
        	mountainsButton.setBackground(Color.lightGray);
        	snowButton.setBackground(Color.GRAY);
        	preview.setBackground(getCurrentColor());
        });
        undoButton.addActionListener(e -> {
        	parent.undo();
        });
        Dimension d = new Dimension(200,30);

        etherButton.setMaximumSize(d);
        etherButton.setMinimumSize(d);
        etherButton.setAlignmentX(CENTER_ALIGNMENT);
        deepButton.setMaximumSize(d);
        deepButton.setMinimumSize(d);
        deepButton.setAlignmentX(CENTER_ALIGNMENT);
        oceanButton.setMaximumSize(d);
        oceanButton.setMinimumSize(d);
        oceanButton.setAlignmentX(CENTER_ALIGNMENT);
        shallowButton.setMaximumSize(d);
        shallowButton.setMinimumSize(d);
        shallowButton.setAlignmentX(CENTER_ALIGNMENT);
        sandButton.setMaximumSize(d);
        sandButton.setMinimumSize(d);
        sandButton.setAlignmentX(CENTER_ALIGNMENT);
        woodButton.setMaximumSize(d);
        woodButton.setMinimumSize(d);
        woodButton.setAlignmentX(CENTER_ALIGNMENT);
        mountainsButton.setMaximumSize(d);
        mountainsButton.setMinimumSize(d);
        mountainsButton.setAlignmentX(CENTER_ALIGNMENT);
        snowButton.setMaximumSize(d);
        snowButton.setMinimumSize(d);
        snowButton.setAlignmentX(CENTER_ALIGNMENT);
        
        undoButton.setMaximumSize(d);
        undoButton.setMinimumSize(d);
        undoButton.setAlignmentX(CENTER_ALIGNMENT);
        
        this.add(etherButton);
        this.add(deepButton);
        this.add(oceanButton);
        this.add(shallowButton);
        this.add(sandButton);
        this.add(woodButton);
        this.add(mountainsButton);
        this.add(snowButton);
        
        this.add(preview);
        
        this.add(toolPanel);
        
        

        
        this.add(undoButton);
        this.add(Box.createVerticalGlue());
        JPanel p = new JPanel(new FlowLayout());
        p.setMaximumSize(new Dimension(200,30));
        p.add(quitButton);
        p.add(saveButton);
        this.add(p);        
	}


	public Color getCurrentColor() {
		try{
			return new Color(current.getRGB());
		}
		catch(NullPointerException e){
			return null;
		}
	}

	public Tool getTool() {
		return (Tool) toolBox.getSelectedItem();
	}
}
