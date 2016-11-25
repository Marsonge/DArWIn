package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;

import controler.WorldControler;

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
	private JLabel nbTime;
	private JLabel nbAlive;
	private JLabel nbDead;
	private int time;
	private int alive;
	private int dead;
	private WorldControler wc;
	private MainView parent;
	private SidePanel self = this;

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
	public SidePanel(MainView mv){
		
		this.parent = mv;
		
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
				return new Dimension(280,660); // Utilite ?
        	}
        };
        
        // Add tabs to tabbedPane
        tabbedPane.addTab("Options", null, tabOptions);
        tabbedPane.addTab("Stats", null, tabStats);
        
        time = 0;
        alive = 90;
        dead = 0;
        
        JPanel titres = new JPanel(new GridLayout(7,2));
        JPanel values = new JPanel(new GridLayout(7,2));
        JPanel ligne = new JPanel (new FlowLayout(4));
        
        JLabel lbTime = new JLabel("Time:");
        JLabel lbAlive = new JLabel("Alive creatures:");
        JLabel lbDead = new JLabel("Death count:");
        
        nbTime = new JLabel(Integer.toString(Math.round(time/10)));
        nbAlive = new JLabel(Integer.toString(alive));
        nbDead = new JLabel(Integer.toString(dead));
        
        titres.add(lbTime);
        titres.add(lbAlive);
        titres.add(lbDead);
        values.add(nbTime);
        values.add(nbAlive);
        values.add(nbDead);
        ligne.add(titres);
        ligne.add(values);

        tabStats.add(ligne);
        
        // Add neural network view button to Stats tab
        JButton viewNnButton = new JButton("View creature's neural network");
        viewNnButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		// We set the value of SidePanel's wc once the button is clicked.
        		 if (wc == null) wc = parent.getWorldControler();
        		 
        		 // NeuralNetwork view is created with current creature's NN.
        		 if (wc.getCurrentCreature() != null){
        			 ViewNeuralNetwork nnView = new ViewNeuralNetwork(wc.getCurrentCreature().getNeuralNetwork());
        			 nnView.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            		 nnView.pack();
            		 nnView.setVisible(true);
        		 }
        	}
        });
        tabStats.add(viewNnButton);
        
        
        // Add tabbedPane to viewPanel
        this.add(tabbedPane);     
	}
	
	public void tick(){
		time++;
		nbTime.setText(Integer.toString(Math.round(time/10)));
		this.revalidate();
		this.repaint();
	}
	
	public void updateNbCreature(int nbAlive,int nbDead){
		this.nbAlive.setText(Integer.toString(nbAlive));
		this.nbDead.setText(Integer.toString(nbDead));
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
