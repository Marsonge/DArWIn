package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
/**
 * 
 * ViewPanel
 * 
 * Side panel with options and stats tab
 * 
 * @author cyril.weller
 *
 */
public class ViewPanel extends JPanel {
	
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
	public ViewPanel(int alive, int dead){
		
		// Set size and color of panel
        this.setPreferredSize(new Dimension(300,700)); 
        this.setBackground(black);
		
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Change map button
        changeMap.setBackground(defaultButtonColor);
        changeMap.setPreferredSize(new Dimension(130,30));
        
        // Start button
        start.setBackground(defaultButtonColor);
        start.setPreferredSize(new Dimension(130,30));

        /** Create the tabs **/
        
        // Options tab
        JPanel tabOptions = new JPanel(){
			
        	private static final long serialVersionUID = 1L;
			
			public Dimension getPreferredSize(){ 
				return new Dimension(280,660); 
			}

        };
        
        // Add buttons to Option tab
        tabOptions.add(changeMap);
        tabOptions.add(start);
         
        // Stats tab
        JPanel tabStats = new JPanel(){
			
        	private static final long serialVersionUID = 1L;

			public Dimension getPreferredSize(){ 
				return new Dimension(280,660); //Utilité ?
        	}
        };
        
        // Add tabs to tabbedPane
        tabbedPane.addTab("Options", null, tabOptions);
        tabbedPane.addTab("Stats", null, tabStats);
        
        //Oui c'est sale
        //Mais je suis nul en java
        String time = "00:00";

        JLabel stats = new JLabel("<html>Temps : <br>"
					        		+ "Nbr bestioles en vie : <br>"
					        		+ "Nbr bestioles crevées : </html>");
        
        JLabel statsValues = new JLabel("<html>"+time+"<br>"
						        		+ alive + "<br>"
						        		+ dead + "</html>");

        
        tabStats.add(stats);
        tabStats.add(statsValues);
        
        // Add tabbedPane to viewPanel
        this.add(tabbedPane);     
	}

}
