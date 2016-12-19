package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utils.EndOfGameEvent;
import utils.EndOfGameEventListener;
import utils.GrowTimerActionListener;
import utils.TimerActionListener;
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
	SidePanel sP = new SidePanel(this);
	public boolean simulationLaunched = false;
	private MainView self = this;
	
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
    	sP = new SidePanel(this);
    	
    	//sP.addPropertyChangeListener();
    	
    	this.add(sP, BorderLayout.EAST);
    	
    	// Add listeners 
    	this.setStartButtonListener(sP);
    	this.setChangeMapButtonListener(sP);
    	this.setNbCreaturesListener(sP);
    	this.setNbCreaturesSoftCapListener(sP);
    	this.setNbCreaturesHardCapListener(sP);
    	
    	// Add a map
    	changeMap();
	}
	
	/**
	 * Start the timer
	 */
	public void initTimer(){
        this.timer = new Timer(TICK_GAMETURN, new TimerActionListener(wc,sP)); 
	}
	
	public void initGrowTimer(){
		this.growTimer = new Timer(TICK_GROW, new GrowTimerActionListener(wc));
	}
	
	/**
	 * Change the map
	 */
	public void changeMap(){
		
		// When map is created the first time, vG is null
		if (vG != null) this.remove(vG);

		
		this.wc = new WorldControler(GRID_SIZE,TILE_SIZE,(float)80*GRID_SIZE,0,NUMBER_OF_CREATURES); 
		this.wc.setSoftCap(sP.getSoftCapSlider().getValue());
		this.wc.setHardCap(sP.getHardCapSlider().getValue());
		this.sP.updateNbCreature(NUMBER_OF_CREATURES, 0);
		this.vG = new ViewGrid(wc);
    	this.setEndOfGameListener(this.vG);
		
		this.add(vG, BorderLayout.WEST);
    	this.pack();
    	this.setVisible(true);
 
    	wc.simulateForward();
    	initTimer();
    	initGrowTimer();
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
	public void setNbCreaturesSoftCapListener(final SidePanel vp){
		
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
	public void setNbCreaturesHardCapListener(final SidePanel vp){
		
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

                    } else if (btn.getText().equals("Pause")){
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
	
	public void setEndOfGameListener(final ViewGrid vg){
		vg.addEndOfGameListener(new EndOfGameEventListener() {
			public void actionPerformed(EndOfGameEvent evt) {
		    	
				int choice = JOptionPane.showConfirmDialog(null, "Your creatures all died ! Do you want to start a new simulation ?", "DArWIn - the end", JOptionPane.YES_NO_OPTION);
				System.out.println("Event triggered");
				if (choice == JOptionPane.YES_OPTION) {
		  			// if yes do : changes map once and reset all buttons settings
		  			self.simulationLaunched = false;
		  			self.changeMap();
		  			self.sP.getChangeMapButton().setEnabled(true);
		  			self.sP.getStartButton().setEnabled(true);
		  			self.sP.getStartButton().setText("Start");
		  		} else {
		  			// if no do : quit game
		  			// TODO Save results before exit
		  			System.exit(0);
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
	
	public WorldControler getWorldControler(){
		return wc;
	}

}
