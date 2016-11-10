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
	
	static final int NUMBER_OF_CREATURES = 90;
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
	ViewPanel vp = new ViewPanel();
	public boolean simulationLaunched = false;
	
	private int NUMBER_OF_CREATURES = vp.getInitialNbSlider().getValue();
	
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
    	vp = new ViewPanel();
    	
    	//vp.addPropertyChangeListener();
    	
    	this.add(vp, BorderLayout.EAST);
    	
    	// Add listeners 
    	this.setStartButtonListener(vp);
    	this.setChangeMapButtonListener(vp);
    	this.setNbCreaturesListener(vp);
    	
    	// Add a map
    	changeMap();
	}
	
	/**
	 * Start the timer
	 */
	public void startTimer(){
        this.timer = new Timer(TICK_GAMETURN, new TimerActionListener(wc)); 
        this.tick = 100;
        this.timer = new Timer(tick, new TimerActionListener(wc,vp)); 
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
	 * @param vp
	 */
	public void setNbCreaturesListener(final ViewPanel vp){
		
		vp.getInitialNbSlider().addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				NUMBER_OF_CREATURES = vp.getInitialNbSlider().getValue();			
			}
			
		});
	}
	
	/**
	 * Will set the start button listener
	 * @param vp
	 */
	public void setStartButtonListener(final ViewPanel vp){
		
        // When start is clicked, simulation start and button displays stop
        // When it is clicked on stop, simulation pauses
        vp.getStartButton().addActionListener(new ActionListener() {
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
                        	vp.disable(vp.getChangeMapButton());
                        	vp.disable(vp.getInitialNbSlider());
                        	vp.disable(vp.getInitialNbLabel());
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
	 * @param vp
	 */
	public void setChangeMapButtonListener(final ViewPanel vp){
		
        vp.getChangeMapButton().addActionListener(new ActionListener() {
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
	
	public static int getNumberOfCreatures() {
		return NUMBER_OF_CREATURES;
	}

	public static int getNumberOfCreaturesDead() {
		return NUMBER_OF_CREATURES_DEAD;
	}

}
