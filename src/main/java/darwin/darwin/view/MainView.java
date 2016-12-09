package darwin.darwin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import darwin.darwin.controler.WorldControler;
import darwin.darwin.utils.EndOfGameEvent;
import darwin.darwin.utils.EndOfGameEventListener;
import darwin.darwin.utils.GrowTimerActionListener;
import darwin.darwin.utils.TimerActionListener;
import darwin.darwin.utils.Utils;

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
	static final int TICK_GAMETURN_MAX = 5000;
	static final int TICK_GAMETURN_MIN = 10;
	static int TICK_GROW = 1000;
	static final int TICK_GROW_MAX = 50000;
	static final int TICK_GROW_MIN = 100;
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
	 * @throws IOException 
	 * 
	 */
	public MainView() throws InterruptedException, IOException {
		
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
    	this.setTimeControlListener(sP);
    	
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
		int seed = 0;
		if(this.sP.getSeed() != 0){
			seed = Utils.borderVar(this.sP.getSeed(), 0, Integer.MAX_VALUE, 0);
		}
		Float[] depths = new Float[7];
		int i = 0;
		List<JSlider> depthSliders = this.sP.getDepthSliders();
		for(JSlider j : depthSliders){
			depths[i] = j.getValue()/(float) 100;
			i++;
		}
		this.wc = new WorldControler(GRID_SIZE,TILE_SIZE,(float)80*GRID_SIZE,seed,NUMBER_OF_CREATURES,depths); 
		this.wc.setSoftCap(sP.getSoftCapSlider().getValue());
		this.wc.setHardCap(sP.getHardCapSlider().getValue());
		this.sP.updateNbCreature(NUMBER_OF_CREATURES, 0);
		this.sP.updateSeed(this.wc.getSeed());
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
                        	sP.disableSliders();
                        	sP.disableLabels();
                        	sP.addTimeControl();
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
				if (choice == JOptionPane.YES_OPTION) {
		  			// if yes do : changes map once and reset all buttons settings
		  			self.simulationLaunched = false;
		  			self.changeMap();
		  			self.sP.getChangeMapButton().setEnabled(true);
		  			self.sP.getStartButton().setEnabled(true);
		  			self.sP.getStartButton().setText("Start");
		  			self.sP.removeTimeControl();
		  			self.sP.enableDepthTailoring();
		  		} else {
		  			// if no do : quit game
		  			// TODO Save results before exit
		  			System.exit(0);
		  		}
		     }
		});
	}
	
	private void setTimeControlListener(SidePanel sP2) {
		sP.getSlow2Button().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
           {
            	changeSpeed(-2);
            }
        });
		sP.getSlow1Button().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
           {
            	changeSpeed(-1);
            }
        });
		sP.getRegularButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
           {
            	changeSpeed(0);
            }
        });
		sP.getFast1Button().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
           {
            	changeSpeed(1);
            }
        });
		sP.getFast2Button().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
           {
            	changeSpeed(2);
            }
        });
	}
	
	protected void changeSpeed(int i) {
		timer.stop();
        growTimer.stop();
        switch (i){
        	case 1:
        		sP.enableDecceleration();
        		TICK_GAMETURN -= 10;
        		if(TICK_GAMETURN < TICK_GAMETURN_MIN){
        			TICK_GAMETURN = TICK_GAMETURN_MIN;
        			sP.disableAcceleration();
        			sP.enableDecceleration();
        		}
        		TICK_GROW -= 100;
        		if(TICK_GROW < TICK_GROW_MIN){
        			TICK_GROW = TICK_GROW_MIN;
        		}
    		break;
        	case 2:
        		TICK_GAMETURN = TICK_GAMETURN_MIN;
        		TICK_GROW = TICK_GROW_MIN;
        		sP.disableAcceleration();
    			sP.enableDecceleration();
    		break;
        	case 0:
        		TICK_GAMETURN = 100;
        		TICK_GROW = 1000;
        		sP.enableAcceleration();
    			sP.enableDecceleration();
			break;
        	case -1:
        		sP.enableAcceleration();
        		TICK_GAMETURN += 10;
        		if(TICK_GAMETURN > TICK_GAMETURN_MAX){
        			TICK_GAMETURN = TICK_GAMETURN_MAX;
        			sP.enableAcceleration();
        			sP.disableDecceleration();
        		}
        		TICK_GROW += 100;
        		if(TICK_GROW > TICK_GROW_MAX){
        			TICK_GROW = TICK_GROW_MAX;
        		}
    		break;
        	case -2:
        		TICK_GAMETURN = TICK_GAMETURN_MAX;
        		TICK_GROW = TICK_GROW_MAX;
        		sP.enableAcceleration();
    			sP.disableDecceleration();
			break;
        }
        
        timer.setDelay(TICK_GAMETURN);
        growTimer.setDelay(TICK_GROW);
        timer.start();
        growTimer.start();
	}
	
	public static int getNumberOfCreaturesDead() {
		return NUMBER_OF_CREATURES_DEAD;
	}
	
	public WorldControler getWorldControler(){
		return wc;
	}
	
	/**
	 * main function of the project, will create the view
	 * 
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		
//		// Noice Look and Feel for the application
//		try {
//			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//		        if ("Nimbus".equals(info.getName())) {
//		            UIManager.setLookAndFeel(info.getClassName());
//		            break;
//		        }
//		    }
//		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//				| UnsupportedLookAndFeelException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		// Launch MainView
		new MainView();
	}

}
