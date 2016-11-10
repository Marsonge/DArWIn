package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controler.WorldControler;

/**
 * 
 * MainView
 * 
 * main view of the project
 * 
 * @author cyril.weller
 *
 */
public class MainView extends JFrame {
	
	static final int NUMBER_OF_CREATURES_DEAD = 0;
	static int TICK_GAMETURN = 100;
	static int TICK_GROW = 1000;
	static final int GRID_SIZE = 129;
	static final int TILE_SIZE = 6;


	private static final long serialVersionUID = 1L;
	public static final Color black = new Color(0,0,0);
	private Timer timer;
	private Timer growTimer;
	WorldControler wc; 
	ViewGrid vG ;
	SidePanel sP = new SidePanel();
	public boolean simulationLaunched = false;
	
	private int NUMBER_OF_CREATURES = sP.getInitialNbSlider().getValue();
	
	/**
	 * MainView()
	 * 
	 * Create the main JFrame and add the ViewGrid 
	 * @throws InterruptedException 
	 * 
	 */
	public MainView() throws InterruptedException {
		
		// Create the main JFrame
		this.setTitle("Darwin : ARtificial Wildlife INtelligence");
		this.setResizable(false);

        // main JFrame setting
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
		this.setBackground(black);
		this.setExtendedState(JFrame.NORMAL); 
        
        // Add Layout to mainFrame
		this.setLayout(new BorderLayout());
    	
    	// Add view panel
    	sP = new SidePanel();
    	
    	//sP.addPropertyChangeListener();
    	
    	this.add(sP, BorderLayout.EAST);
    	
    	// Add listeners 
    	this.setStartButtonListener(vp);
    	this.setChangeMapButtonListener(vp);
    	this.setNbCreaturesListener(vp);
    	this.setNbCreaturesSoftCapListener(vp);
    	this.setNbCreaturesHardCapListener(vp);
    	
    	// Add a map
    	changeMap();
	}
	
	/**
	 * Start the timer
	 */
	public void startTimer(){
        this.timer = new Timer(TICK_GAMETURN, new TimerActionListener(wc,sP)); 
	}
	
	public void startGrowTimer(){
		this.growTimer = new Timer(TICK_GROW, new GrowTimerActionListener(wc));
	}
	
	/**
	 * Change the map
	 */
	public void changeMap(){
		
		// When map is created the first time, vG is null
		if (vG != null) this.remove(vG);
		
		this.wc = new WorldControler(GRID_SIZE,TILE_SIZE,(float)80*GRID_SIZE,0,NUMBER_OF_CREATURES); 
		this.wc.setSoftCap(vp.getSoftCapSlider().getValue());
		this.wc.setHardCap(vp.getHardCapSlider().getValue());
		
		this.vG = new ViewGrid(wc);
		
		this.add(vG, BorderLayout.WEST);
    	this.pack();
    	this.setVisible(true);
 
    	wc.simulateForward();
		startTimer();
		startGrowTimer();
	}
	
	/**
	 * 
	 * @param sP
	 */
	public void setNbCreaturesListener(final SidePanel sP){
		
		sP.getInitialNbSlider().addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				NUMBER_OF_CREATURES = sP.getInitialNbSlider().getValue();			
			}
			
		});
	}
	
	/**
	 * 
	 * @param vp
	 */
	public void setNbCreaturesSoftCapListener(final ViewPanel vp){
		
		vp.getSoftCapSlider().addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				wc.setSoftCap(vp.getSoftCapSlider().getValue());			
			}
			
		});
	}
	
	/**
	 * 
	 * @param vp
	 */
	public void setNbCreaturesHardCapListener(final ViewPanel vp){
		
		vp.getHardCapSlider().addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				wc.setHardCap(vp.getHardCapSlider().getValue());			
			}
			
		});
	}
	
	/**
	 * Will set the start button listener
	 * @param sP
	 */
	public void setStartButtonListener(final SidePanel sP){
		
        // When start is clicked, simulation start and button displays stop
        // When it is clicked on stop, simulation pauses
        sP.getStartButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
           {
                Object source = e.getSource();
                if (source instanceof JButton) {
                	
                    JButton btn = (JButton)source;
          		
                    if(btn.getText().equals("Start")){ 
                		
                        timer.start();
                        growTimer.start();
                        btn.setText("Pause");
                        if (simulationLaunched == false) {
                        	simulationLaunched = true;
                        	sP.disable(sP.getChangeMapButton());
                        	sP.disable(sP.getInitialNbSlider());
                        	sP.disable(sP.getInitialNbLabel());
                        }

                    } else{
                    	timer.stop();
                        growTimer.stop();
                    	btn.setText("Start");
                    }

                }
            }
        });
	}
	
	/**
	 * setChangeMapButtonListener
	 * 
	 * Will set the change map button listener 
	 * 
	 * @param sP
	 */
	public void setChangeMapButtonListener(final SidePanel sP){
		
        sP.getChangeMapButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
           {
            	if (!simulationLaunched){
            		changeMap();
            		}
            }
        });
	}
	
	
	/**
	 * main function of the project, will create the view
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		new MainView();
	}
	

	public static int getNumberOfCreaturesDead() {
		return NUMBER_OF_CREATURES_DEAD;
	}

}
