package darwin.darwin.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import darwin.darwin.model.grid.Terrain;
import darwin.darwin.utils.Tool;

public class ViewPalette extends JPanel {
	
	private final ViewMapEditor parent;
    private Terrain t = Terrain.values()[0];
    private final JComboBox colorBox;
    private final JComboBox toolBox;

    
	public ViewPalette(ViewMapEditor parent){
		super();
		this.parent = parent;
		this.setBackground(new Color(t.getRGB()));
		colorBox = new JComboBox();
		toolBox = new JComboBox();
        for (Terrain t : Terrain.values()) {
            colorBox.addItem(t.name());
        }
        for(Tool t : Tool.values()){
        	toolBox.addItem(t);
        }
        colorBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Terrain t = (Terrain)  Terrain.valueOf((String)colorBox.getSelectedItem());
                ViewPalette.this.setBackground(new Color(t.getRGB()));
            }
        });
        this.add(colorBox);
        this.add(toolBox);
	}


	public Color getCurrentColor() {
		return new Color(Terrain.valueOf((String)this.colorBox.getSelectedItem()).getRGB());
	}


	public Tool getTool() {
		return (Tool) toolBox.getSelectedItem();
	}
}
