package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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
	 * Disables a button
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
	 * Enables a button
	 * @param button
	 */
	public void enable(JButton button){
		button.setBorderPainted(true);
		button.setFocusPainted(true);
		button.setEnabled(true);
		button.setFocusable(true);
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
				return new Dimension(280,660);
        	}
        };
        
        JPanel creaturesInfoPanel = new JPanel(){

			private static final long serialVersionUID = 1L;

			public Dimension getPreferredSize(){ 
				return new Dimension(140,330);
        	}
        };
        
        JPanel creaturesInfoTitles = new JPanel(new GridLayout(7,2));
        JPanel creaturesInfoValues = new JPanel(new GridLayout(7,2));
        JPanel creaturesInfoLines = new JPanel(new FlowLayout(4));
        
        JLabel creatureSpeedLabel = new JLabel("Speed : ");
        JLabel creatureEnergyLabel = new JLabel("Energy : ");
        JLabel creatureSpeedValue = new JLabel("10");
        JLabel creatureEnergyValue = new JLabel("50");
        
        creaturesInfoTitles.add(creatureSpeedLabel);
        creaturesInfoTitles.add(creatureEnergyLabel);
        creaturesInfoValues.add(creatureSpeedValue);
        creaturesInfoValues.add(creatureEnergyValue);
        creaturesInfoLines.add(creaturesInfoTitles);
        creaturesInfoLines.add(creaturesInfoValues);
        
        creaturesInfoPanel.add(creaturesInfoLines);
        
        tabStats.add(creaturesInfoPanel);
        
        // Add tabs to tabbedPane
        tabbedPane.addTab("Options", null, tabOptions);
        tabbedPane.addTab("Stats", null, tabStats);
        
        // Add tabbedPane to viewPanel
        this.add(tabbedPane);     
	}

}
