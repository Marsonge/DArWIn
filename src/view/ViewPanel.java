package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * ViewPanel
 * 
 * Side panel with options
 * 
 * @author cyril.weller
 *
 */
public class ViewPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	public static final Color black = new Color(0,0,0);
	public static final Color defaultButtonColor = new Color(220,220,220);

	JButton changeMap = new JButton("Change Map");
	JButton start = new JButton("Start");
	
	/**
	 * Disable a button
	 * 
	 * @param button
	 */
	public void disable(JButton button){
		//button.setBackground(defaultButtonColorDisabled);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setEnabled(false);
		button.setFocusable(false);
	}
	
	/**
	 * get the start button
	 * @return
	 */
	public JButton getStartButton(){
		return this.start;
	}
	
	/**
	 * get the change map button
	 * @return
	 */
	public JButton getChangeMapButton(){
		return this.changeMap;
	}

	/**
	 * ViewPanel constructor, will build the side panel
	 */
	public ViewPanel(){

		//JPanel jpanel = new JPanel();
        this.setPreferredSize(new Dimension(500,700)); 
        this.setBackground(black);
        
        changeMap.setBackground(defaultButtonColor);
        start.setBackground(defaultButtonColor);
        
        // Start button added to JPanel
        this.add(changeMap);
        this.add(start);

	}

}
