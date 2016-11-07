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

	private static final long serialVersionUID = 1L;
	public static final Color black = new Color(0,0,0);
	private Timer timer;
	private int tick;
	private Timer growTimer;
	private int growTick;
	WorldControler wc ; 
	ViewGrid vG ;
	public boolean simulationLaunched = false;
	
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
    	ViewPanel vp = new ViewPanel();
    	
    	this.add(vp, BorderLayout.EAST);
    	
    	// Add listeners 
    	this.setStartButtonListener(vp);
    	this.setChangeMapButtonListener(vp);
    	
    	// Add a map
    	changeMap();
	}
	
	/**
	 * Start the timer
	 */
	public void startTimer(){
        this.tick = 100;
        this.timer = new Timer(tick, new TimerActionListener(wc)); 
	}
	public void startGrowTimer(){
		this.growTick = 1000;
		this.growTimer = new Timer(growTick, new GrowTimerActionListener(wc));
	}
	
	/**
	 * Change the map
	 */
	public void changeMap(){
		
		// When map is created the first time, vG is null
		if (vG != null) this.remove(vG);
		
		this.wc = new WorldControler(100,7,(float)10000,0,90); 
		this.vG = new ViewGrid(wc);
		
		this.add(vG, BorderLayout.WEST);
    	this.pack();
    	this.setVisible(true);
    	
		wc.simulateForward();
		startTimer();
		startGrowTimer();
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

}
