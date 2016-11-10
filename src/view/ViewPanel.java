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
/**
 * 
 * ViewPanel
 * 
 * Side panel with options and stats tab
 * 
 * @author cyril.weller
 *
 */
public class ViewPanel extends JPanel implements Observer{
	
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
}
