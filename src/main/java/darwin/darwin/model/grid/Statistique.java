package darwin.darwin.model.grid;

import javax.swing.JButton;
import javax.swing.JPanel; 

/**
 * The grid which contains all Tiles
 * Generated with fractals
 *
 */
public class Statistique {
	
	private JPanel jpanel;
	
	public Statistique(){
		jpanel = new JPanel();
		JButton ButtondeMerdeQuiSertaRien = new JButton("Nom qui tue");
		jpanel.add(ButtondeMerdeQuiSertaRien);
	}

	public JPanel getStatistique(){
		return jpanel;
	}

	

}
