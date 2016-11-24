package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.naming.InitialContext;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;




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
	public static int MAX_CRITICAL_CREATURE = 1000;
	public static int DEFAULT_CREATURE = 30;
	public static int DEFAULT_PREFERRED_CREATURE = 400;
	public static int DEFAULT_CRITICAL_CREATURE = 550;
	public static int MIN_DEPTH = 0;
	public static int MAX_DEPTH = 100;
	public static int DEEP_WATER = 0;
	public static int OCEAN = 15;
	public static int SHALLOW_WATER = 30;
	public static int SAND = 43;
	public static int WOOD = 52;
	public static int MOUNTAIN = 75;
	public static int SNOW = 88;
	
	private static final long serialVersionUID = 1L;
	public static final Color black = new Color(0,0,0);
	public static final Color defaultButtonColor = new Color(220,220,220);
	private JLabel nbTime;
	private JLabel nbAlive;
	private JLabel nbDead;
	private int time;
	private int alive;
	private int dead;
	private List<JSlider> depthSliders;
	
	JButton changeMap = new JButton("Change Map");
	JButton start = new JButton("Start");
	JLabel nbCreaturesLabel = new JLabel("Initial number of creatures");
	JTextField nbCreaturesTextField = new JTextField(4);
	JSlider nbCreatures = new JSlider(MIN_CREATURE, MAX_CREATURE, DEFAULT_CREATURE);
	JLabel nbCreaturesPreferredLabel = new JLabel("Preferred number of creatures");
	JSlider nbCreaturesPreferred = new JSlider(MIN_CRITICAL_CREATURE, MAX_CRITICAL_CREATURE, DEFAULT_PREFERRED_CREATURE);
	JTextField nbCreaturesPreferredTextField = new JTextField(4);
	JLabel nbCreaturesCriticalLabel = new JLabel("Critical number of creatures");
	JSlider nbCreaturesCritical = new JSlider(MIN_CRITICAL_CREATURE, MAX_CRITICAL_CREATURE, DEFAULT_CRITICAL_CREATURE);
	JLabel deepOceanLabel = new JLabel("Deep ocean");
	JSlider deepOceanSlider = new JSlider(MIN_DEPTH, MAX_DEPTH, DEEP_WATER);
	JLabel oceanLabel = new JLabel("Ocean");
	JSlider oceanSlider = new JSlider(MIN_DEPTH, MAX_DEPTH, OCEAN);
	JLabel waterLabel = new JLabel("Coast");
	JSlider waterSlider = new JSlider(MIN_DEPTH, MAX_DEPTH, SHALLOW_WATER);
	JLabel sandLabel = new JLabel("Sand");
	JSlider sandSlider = new JSlider(MIN_DEPTH, MAX_DEPTH, SAND);
	JLabel woodLabel = new JLabel("Woods");
	JSlider woodSlider = new JSlider(MIN_DEPTH, MAX_DEPTH, WOOD);
	JLabel mountainsLabel = new JLabel("Mountains");
	JSlider mountainsSlider = new JSlider(MIN_DEPTH, MAX_DEPTH, MOUNTAIN);
	JLabel snowLabel = new JLabel("Snow");
	JSlider snowSlider = new JSlider(MIN_DEPTH, MAX_DEPTH, SNOW);
	JTextField nbCreaturesCriticalTextField = new JTextField(4);
	JTextField textSeed = new JTextField(9);
	JLabel labelCSeed = new JLabel("Current seed:");
	JLabel cSeed = new JLabel("");
	JLabel labelSeed = new JLabel("Input seed for generation:");
	JLabel custLabel = new JLabel("Customize map depths");
	
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
	
	public void disableLabels(){
		this.disable(snowLabel);
		this.disable(mountainsLabel);
		this.disable(woodLabel);
		this.disable(sandLabel);
		this.disable(waterLabel);
		this.disable(oceanLabel);
		this.disable(deepOceanLabel);
		this.disable(nbCreaturesLabel);
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
	
	public List<JSlider> getDepthSliders(){
		return this.depthSliders;
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
	 * SidePanel constructor, will build the side panel
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
        
        initDepthSliders();
        
        /** Slider **/
        nbCreatures.setMinorTickSpacing(5);
        nbCreatures.setMajorTickSpacing(20);
        nbCreatures.setPaintTicks(true);
        nbCreatures.setPaintLabels(true);
        nbCreaturesTextField.setText(Integer.toString(DEFAULT_CREATURE));
        /** Slider **/
        nbCreaturesCritical.setMinorTickSpacing(25);
        nbCreaturesCritical.setMajorTickSpacing(200);
        nbCreaturesCritical.setPaintTicks(true);
        nbCreaturesCritical.setPaintLabels(true);
        nbCreaturesCriticalTextField.setText(Integer.toString(DEFAULT_CRITICAL_CREATURE));
        
        /** Slider **/
        nbCreaturesPreferred.setMinorTickSpacing(25);
        nbCreaturesPreferred.setMajorTickSpacing(200);
        nbCreaturesPreferred.setPaintTicks(true);
        nbCreaturesPreferred.setPaintLabels(true);
        nbCreaturesPreferredTextField.setText(Integer.toString(DEFAULT_PREFERRED_CREATURE));
        
        addSliderListeners();
        
        
        //Add sliders to Option tab
        tabOptions.add(nbCreaturesLabel);
        tabOptions.add(nbCreaturesTextField);
        tabOptions.add(nbCreatures);
        
        tabOptions.add(nbCreaturesPreferredLabel);
        tabOptions.add(nbCreaturesPreferredTextField);
        tabOptions.add(nbCreaturesPreferred);
        
        tabOptions.add(nbCreaturesCriticalLabel);
        tabOptions.add(nbCreaturesCriticalTextField);
        tabOptions.add(nbCreaturesCritical);
        
        Dimension d = new Dimension(80,15);
        cSeed.setMinimumSize(d);
        cSeed.setPreferredSize(d);
        cSeed.setMaximumSize(d);
        Dimension d1 = new Dimension(100,15);
        labelCSeed.setMinimumSize(d1);
        labelCSeed.setPreferredSize(d1);
        labelCSeed.setMaximumSize(d1);
        
        
        // Add buttons to Option tab
        tabOptions.add(changeMap);
        tabOptions.add(start);
        tabOptions.add(labelCSeed);
        tabOptions.add(cSeed);
        tabOptions.add(labelSeed);
        tabOptions.add(textSeed);
        
        tabOptions.add(deepOceanLabel);
        tabOptions.add(deepOceanSlider);

        tabOptions.add(oceanLabel);
        tabOptions.add(oceanSlider);

        tabOptions.add(waterLabel);
        tabOptions.add(waterSlider);

        tabOptions.add(sandLabel);
        tabOptions.add(sandSlider);

        tabOptions.add(woodLabel);
        tabOptions.add(woodSlider);

        tabOptions.add(mountainsLabel);
        tabOptions.add(mountainsSlider);
        
        tabOptions.add(snowLabel);
        tabOptions.add(snowSlider);
         
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
        
        // Add tabbedPane to viewPanel
        this.add(tabbedPane);     
	}
	
	private void initDepthSliders() {
		int width = 70;
		int height = 15;
		Dimension d = new Dimension(width,height);
		
        this.depthSliders = new ArrayList<JSlider>();
        deepOceanSlider.setMinorTickSpacing(5);
        deepOceanSlider.setMajorTickSpacing(20);
        deepOceanSlider.setPaintTicks(true);
        deepOceanSlider.setPaintLabels(true);
        deepOceanLabel.setMinimumSize(d);
        deepOceanLabel.setPreferredSize(d);
        deepOceanLabel.setMaximumSize(d);
        
        oceanSlider.setMinorTickSpacing(5);
        oceanSlider.setMajorTickSpacing(20);
        oceanSlider.setPaintTicks(true);
        oceanSlider.setPaintLabels(true);
        oceanLabel.setMinimumSize(d);
        oceanLabel.setPreferredSize(d);
        oceanLabel.setMaximumSize(d);
        
        waterSlider.setMinorTickSpacing(5);
        waterSlider.setMajorTickSpacing(20);
        waterSlider.setPaintTicks(true);
        waterSlider.setPaintLabels(true);
        waterLabel.setMinimumSize(d);
        waterLabel.setPreferredSize(d);
        waterLabel.setMaximumSize(d);
        
        sandSlider.setMinorTickSpacing(5);
        sandSlider.setMajorTickSpacing(20);
        sandSlider.setPaintTicks(true);
        sandSlider.setPaintLabels(true);
        sandLabel.setMinimumSize(d);
        sandLabel.setPreferredSize(d);
        sandLabel.setMaximumSize(d);
        
        woodSlider.setMinorTickSpacing(5);
        woodSlider.setMajorTickSpacing(20);
        woodSlider.setPaintTicks(true);
        woodSlider.setPaintLabels(true);
        woodLabel.setMinimumSize(d);
        woodLabel.setPreferredSize(d);
        woodLabel.setMaximumSize(d);
        
        mountainsSlider.setMinorTickSpacing(5);
        mountainsSlider.setMajorTickSpacing(20);
        mountainsSlider.setPaintTicks(true);
        mountainsSlider.setPaintLabels(true);
        mountainsLabel.setMinimumSize(d);
        mountainsLabel.setPreferredSize(d);
        mountainsLabel.setMaximumSize(d);
        
        snowSlider.setMinorTickSpacing(5);
        snowSlider.setMajorTickSpacing(20);
        snowSlider.setPaintTicks(true);
        snowSlider.setPaintLabels(true);
        snowLabel.setMinimumSize(d);
        snowLabel.setPreferredSize(d);
        snowLabel.setMaximumSize(d);
        
        
        
        depthSliders.add(snowSlider);
        depthSliders.add(mountainsSlider);
        depthSliders.add(woodSlider);
        depthSliders.add(sandSlider);
        depthSliders.add(waterSlider);
        depthSliders.add(oceanSlider);
        depthSliders.add(deepOceanSlider);

	}

	public void addSliderListeners(){
		//nbCreatures
		nbCreatures.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
            	nbCreaturesTextField.setText(String.valueOf(nbCreatures.getValue()));
            }
        });
        nbCreaturesTextField.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
                String typed = nbCreaturesTextField.getText();
                nbCreatures.setValue(0);
                if(!typed.matches("\\d+") || typed.length() > 10) {
                    return;
                }
                int value = Integer.parseInt(typed);
                nbCreatures.setValue(value);
            }
        });
        
		//Preferred
		nbCreaturesPreferred.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
            	nbCreaturesPreferredTextField.setText(String.valueOf(nbCreaturesPreferred.getValue()));
            }
        });
        nbCreaturesPreferredTextField.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
                String typed = nbCreaturesPreferredTextField.getText();
                nbCreaturesPreferred.setValue(0);
                if(!typed.matches("\\d+") || typed.length() > 10) {
                    return;
                }
                int value = Integer.parseInt(typed);
                nbCreaturesPreferred.setValue(value);
            }
        });
        
        //Critical
        nbCreaturesCritical.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
            	nbCreaturesCriticalTextField.setText(String.valueOf(nbCreaturesCritical.getValue()));
            }
        });
        nbCreaturesCriticalTextField.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
                String typed = nbCreaturesCriticalTextField.getText();
                nbCreaturesCritical.setValue(0);
                if(!typed.matches("\\d+") || typed.length() > 10) {
                    return;
                }
                int value = Integer.parseInt(typed);
                nbCreaturesCritical.setValue(value);
            }
        });
        //Deep Ocean
        deepOceanSlider.addChangeListener(new ChangeListener(){
        	@Override
            public void stateChanged(ChangeEvent e) {
            	if(deepOceanSlider.getValue() > oceanSlider.getValue()){
            		oceanSlider.setValue(deepOceanSlider.getValue());
            	}
            }
        });
        //Ocean
        oceanSlider.addChangeListener(new ChangeListener(){
        	@Override
            public void stateChanged(ChangeEvent e) {
            	if(oceanSlider.getValue() > waterSlider.getValue()){
            		waterSlider.setValue(oceanSlider.getValue());
            	}
            	if(oceanSlider.getValue() < deepOceanSlider.getValue()){
            		deepOceanSlider.setValue(oceanSlider.getValue());
            	}
            }
        });
       //Shallow water
        waterSlider.addChangeListener(new ChangeListener(){
        	@Override
            public void stateChanged(ChangeEvent e) {
            	if(waterSlider.getValue() > sandSlider.getValue()){
            		sandSlider.setValue(waterSlider.getValue());
            	}
            	if(waterSlider.getValue() < oceanSlider.getValue()){
            		oceanSlider.setValue(waterSlider.getValue());
            	}
            }
        });
      //Sand
        sandSlider.addChangeListener(new ChangeListener(){
        	@Override
            public void stateChanged(ChangeEvent e) {
            	if(sandSlider.getValue() > woodSlider.getValue()){
            		woodSlider.setValue(sandSlider.getValue());
            	}
            	if(sandSlider.getValue() < waterSlider.getValue()){
            		waterSlider.setValue(sandSlider.getValue());
            	}
            }
        });
      //Woods
        woodSlider.addChangeListener(new ChangeListener(){
        	@Override
            public void stateChanged(ChangeEvent e) {
            	if(woodSlider.getValue() > mountainsSlider.getValue()){
            		mountainsSlider.setValue(woodSlider.getValue());
            	}
            	if(woodSlider.getValue() < sandSlider.getValue()){
            		sandSlider.setValue(woodSlider.getValue());
            	}
            }
        });
      //Mountains
        mountainsSlider.addChangeListener(new ChangeListener(){
        	@Override
            public void stateChanged(ChangeEvent e) {
            	if(mountainsSlider.getValue() > snowSlider.getValue()){
            		snowSlider.setValue(mountainsSlider.getValue());
            	}
            	if(mountainsSlider.getValue() < woodSlider.getValue()){
            		woodSlider.setValue(mountainsSlider.getValue());
            	}
            }
        });
      //Snow
        snowSlider.addChangeListener(new ChangeListener(){
        	@Override
            public void stateChanged(ChangeEvent e) {
            	if(snowSlider.getValue() < mountainsSlider.getValue()){
            		mountainsSlider.setValue(snowSlider.getValue());
            	}
            }
        });
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

	public void updateSeed(int i){
		this.cSeed.setText(Integer.toString(i));
	}
	public int getSeed(){
		if((textSeed.getText() != null) && !(textSeed.getText().equals(""))){
			try{
				return Integer.parseInt(this.textSeed.getText());
			}
			catch(NumberFormatException e){
				textSeed.setText("");
				return 0;
			}
		}
		return 0;
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
