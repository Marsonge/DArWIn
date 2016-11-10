package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;

import javafx.beans.value.ChangeListener;

/**
 * 
 * ViewPanel
 * 
 * Side panel with options and stats tab
 * 
 * @author cyril.weller
 *
 */
public class SidePanel extends JPanel implements Observer{
	
	public static int MIN_CREATURE = 0;
	public static int MAX_CREATURE = 100;
	public static int MIN_CRITICAL_CREATURE = 0;
	public static int CRITICAL_CREATURE = 1000;
	public static int DEFAULT_CREATURE = 30;
	public static int DEFAULT_PREFERRED_CREATURE = 400;
	public static int DEFAULT_CRITICAL_CREATURE = 550;
	
	private static final long serialVersionUID = 1L;
	public static final Color black = new Color(0,0,0);
	public static final Color defaultButtonColor = new Color(220,220,220);
	private JLabel NbTime;
	private JLabel NbAlive;
	private JLabel NbDead;
	private int time;
	private int alive;
	private int dead;

	JButton changeMap = new JButton("Change Map");
	JButton start = new JButton("Start");
	JLabel nbCreaturesLabel = new JLabel("Initial number of creatures");
	JSlider nbCreatures = new JSlider(MIN_CREATURE, MAX_CREATURE, DEFAULT_CREATURE);
	JLabel nbCreaturesPreferredLabel = new JLabel("Preferred number of creatures");
	JSlider nbCreaturesPreferred = new JSlider(MIN_CRITICAL_CREATURE, CRITICAL_CREATURE, DEFAULT_PREFERRED_CREATURE);
	JLabel nbCreaturesCriticalLabel = new JLabel("Critical number of creatures");
	JSlider nbCreaturesCritical = new JSlider(MIN_CRITICAL_CREATURE, CRITICAL_CREATURE, DEFAULT_CRITICAL_CREATURE);
	
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
	 * Disable a slider
	 * 
	 * @param slider
	 */
	public void disable(JSlider slider){
		slider.setFocusable(false);
		slider.setEnabled(false);
	}
	
	/**
	 * Disable a label
	 * 
	 * @param label
	 */
	public void disable(JLabel label){
		label.setFocusable(false);
		label.setEnabled(false);
	}
	
	/**
	 * 
	 * @return
	 */
	public JSlider getSoftCapSlider(){
		return this.nbCreaturesPreferred;
	}
	
	/**
	 * 
	 * @return
	 */
	public JSlider getHardCapSlider(){
		return this.nbCreaturesCritical;
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
	public SidePanel(){
		
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
        
        /** Slider **/
        nbCreatures.setMinorTickSpacing(5);
        nbCreatures.setMajorTickSpacing(20);
        nbCreatures.setPaintTicks(true);
        nbCreatures.setPaintLabels(true);
        
        /** Slider **/
        nbCreaturesCritical.setMinorTickSpacing(25);
        nbCreaturesCritical.setMajorTickSpacing(200);
        nbCreaturesCritical.setPaintTicks(true);
        nbCreaturesCritical.setPaintLabels(true);
        
        /** Slider **/
        nbCreaturesPreferred.setMinorTickSpacing(25);
        nbCreaturesPreferred.setMajorTickSpacing(200);
        nbCreaturesPreferred.setPaintTicks(true);
        nbCreaturesPreferred.setPaintLabels(true);
        
        // Add buttons to Option tab
        tabOptions.add(nbCreaturesLabel);
        tabOptions.add(nbCreatures);
        tabOptions.add(nbCreaturesPreferredLabel);
        tabOptions.add(nbCreaturesPreferred);
        tabOptions.add(nbCreaturesCriticalLabel);
        tabOptions.add(nbCreaturesCritical);
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
        

        time = 0;
        alive = 90;
        dead = 0;
        
        JPanel Titres = new JPanel(new GridLayout(7,2));
        JPanel Values = new JPanel(new GridLayout(7,2));
        JPanel Ligne = new JPanel (new FlowLayout(4));
        
        JLabel LbTime = new JLabel("Temps :");
        JLabel LbAlive = new JLabel("Nbr bestioles en vie :");
        JLabel LbDead = new JLabel("Nbr bestioles mortes :");
        
        NbTime = new JLabel(Integer.toString(time));
        NbAlive = new JLabel(Integer.toString(alive));
        NbDead = new JLabel(Integer.toString(dead));
        
        Titres.add(LbTime);
        Titres.add(LbAlive);
        Titres.add(LbDead);
        Values.add(NbTime);
        Values.add(NbAlive);
        Values.add(NbDead);
        Ligne.add(Titres);
        Ligne.add(Values);

        tabStats.add(Ligne);
        
        // Add tabbedPane to viewPanel
        this.add(tabbedPane);     
	}
	
	public void tick(){
		time++;
		NbTime.setText(Integer.toString(time));
		this.revalidate();
		this.repaint();
	}
	
	public void NbCreatureUpdate(int nbAlive,int nbDead){
		
		NbAlive.setText(Integer.toString(nbAlive));
		NbDead.setText(Integer.toString(nbDead));
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * getInitialNbSlider
	 * @return
	 */
	public JSlider getInitialNbSlider() {
		return this.nbCreatures;
	}
	
	/**
	 * getInitialNbLabel
	 * @return
	 */
	public JLabel getInitialNbLabel() {
		return this.nbCreaturesLabel;
	}
	
}
