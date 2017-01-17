package darwin.darwin.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import darwin.darwin.model.grid.Terrain;
import darwin.darwin.utils.Tool;

public class ViewPalette extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2195288634007240391L;
	private final ViewMapEditor parent;
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

    private final JComboBox toolBox;
    private final JButton saveButton = new JButton("Save");
    private final JButton quitButton = new JButton("Quit");
    
	public ViewPalette(ViewMapEditor parent){
		super();
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		this.parent = parent;
		this.setBackground(new Color(t.getRGB()));
		toolBox = new JComboBox();
        
        for(Tool t : Tool.values()){
        	toolBox.addItem(t);
        }
        
        current = Terrain.ETHER;
        saveButton.addActionListener(e -> {
        	parent.save();
        });
        quitButton.addActionListener(e-> {
        	parent.dispose();
        });
        etherButton.addActionListener(e -> {
        	current = Terrain.ETHER;
        	this.setBackground(getCurrentColor());
        });
        deepButton.addActionListener(e -> {
        	current = Terrain.DEEP_WATER;
        	this.setBackground(getCurrentColor());
        });
        oceanButton.addActionListener(e -> {
        	current = Terrain.OCEAN;
        	this.setBackground(getCurrentColor());
        });
        shallowButton.addActionListener(e -> {
        	current = Terrain.SHALLOW_WATER;
        	this.setBackground(getCurrentColor());
        });
        sandButton.addActionListener(e -> {
        	current = Terrain.SAND;
        	this.setBackground(getCurrentColor());
        });
        woodButton.addActionListener(e -> {
        	current = Terrain.WOODS;
        	this.setBackground(getCurrentColor());
        });
        mountainsButton.addActionListener(e -> {
        	current = Terrain.MOUNTAINS;
        	this.setBackground(getCurrentColor());
        });
        snowButton.addActionListener(e -> {
        	current = Terrain.SNOW;
        	this.setBackground(getCurrentColor());
        });
        undoButton.addActionListener(e -> {
        	parent.undo();
        });
        
        this.add(etherButton);
        this.add(deepButton);
        this.add(oceanButton);
        this.add(shallowButton);
        this.add(sandButton);
        this.add(woodButton);
        this.add(mountainsButton);
        this.add(snowButton);
        
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
        
        toolBox.setMaximumSize(d);
        this.add(toolBox);
        this.add(undoButton);
        this.add(Box.createVerticalGlue());
        JPanel p = new JPanel(new FlowLayout());
        p.setMaximumSize(new Dimension(200,30));
        p.add(quitButton);
        p.add(saveButton);
        this.add(p);
        
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(); 
        int height = gd.getDisplayMode().getHeight();
        if(height < 900){
        	this.add(Box.createRigidArea(new Dimension(200,200)));
        }
        
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
