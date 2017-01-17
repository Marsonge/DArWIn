package darwin.darwin.view;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import darwin.darwin.controler.WorldControler;

public class TabMap extends JPanel {

	WorldControler wc;
	private SidePanel self;
	MainView parent;
	
	JButton editMapButton = new JButton("Edit map");

	public TabMap(MainView parent, SidePanel self){
		this.self = self;
		this.parent = parent;
		wc = parent.getWorldControler();
		editMapButton.setPreferredSize(new Dimension(200, 30));
		this.add(editMapButton);	
		this.addActionListenerMap();

	}
	
	private void addActionListenerMap() {
		editMapButton.addActionListener(e -> {
			wc = parent.getWorldControler();
			wc.editMap();
	
		});
	}
}
