package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import controler.WorldControler;
import model.grid.Grid;

public class MainView extends JFrame {
	
	public static final Color black = new Color(0,0,0);
	private Timer timer;
	private int tick;
	WorldControler wc ; 
	ViewGrid vG ;
	
	/**
	 * MainView()
	 * 
	 * Create the main JFrame and add the ViewGrid 
	 * @throws InterruptedException 
	 * 
	 */
	public MainView() throws InterruptedException {
		
		// Create the main JFrame
		JFrame mainFrame = new JFrame("Darwin : ARtificial Wildlife INtelligence");
        mainFrame.setResizable(false);

        // main JFrame setting
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
        mainFrame.setBackground(black);
        mainFrame.setExtendedState(JFrame.NORMAL); 
        
        // Add Layout to mainFrame
        mainFrame.setLayout(new BorderLayout());
		
		// Create the grid
		this.wc = new WorldControler(100,7,(float)10000,0,60); 
    	this.vG = new ViewGrid(wc);
    	
    	this.timer = new Timer(tick, new TimerActionListener(wc));
        
        JPanel jpanel = new JPanel();
        jpanel.setPreferredSize(new Dimension(500,700)); 
        jpanel.setBackground(black);

        // Start Button
        JButton start = new JButton("Start");
        
        
        // When start is clicked, simulation start and button displays stop
        // When it is clicked on stop, simulation pauses
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                Object source = e.getSource();
                if (source instanceof JButton) {
                	
                    JButton btn = (JButton)source;
                    
                    if(btn.getText().equals("Start")){
                        timer.start();
                        btn.setText("Pause");

                    } else{
                    	timer.stop();
                    	btn.setText("Start");
                    }

                }
            }
        });
        
        // Start button added to JPanel
        jpanel.add(start);
        
        // Adding viewGrid to mainFrame
        mainFrame.add(jpanel, BorderLayout.EAST);
        mainFrame.add(vG, BorderLayout.WEST);
        mainFrame.pack();
        mainFrame.setVisible(true);

		Thread.sleep(1000);
		wc.simulateForward();
        
        // Start main timer
        this.tick = 100;
        this.timer = new Timer(tick, new TimerActionListener(wc));  
	}
	
	
	/**
	 * main function of the project, will create the view
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		
		JFrame mainPanel = new MainView(); 
	}

}
