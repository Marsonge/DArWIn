package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import controler.WorldControler;
import model.grid.Grid;

public class MainView extends JFrame {
	
	public static final Color black = new Color(0,0,0);
	private final Timer timer;
	private int tick;
	
	/**
	 * MainView()
	 * 
	 * Create the main JFrame and add the ViewGrid 
	 * @throws InterruptedException 
	 * 
	 */
	public MainView() throws InterruptedException{
		
		// Create the main JFrame
		JFrame mainFrame = new JFrame("Darwin : ARtificial Wildlife Intelligence");
		
		// Create the grid
		WorldControler wc = new WorldControler(100,8,(float)10000,0,60); 
        ViewGrid vG = new ViewGrid(wc);
        
        wc.getStatistique();
        
        mainFrame.setLayout(new BorderLayout());

        
        mainFrame.getContentPane().add(wc.getStatistique(), BorderLayout.NORTH);
        //mainFrame.getContentPane().add(new JPanel("test"), BorderLayout.NORTH);
        mainFrame.getContentPane().add(vG, BorderLayout.CENTER);
        
        wc.getCountCreature(); //Nombre de créatures
        
        
        //System.exit(0);
        
        // Adding viewGrid to mainFrame
        //mainFrame.add(vG);
        //mainFrame.add(wc.getStatistique());
        
        
        
        // main JFrame setting
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBackground(black);
        mainFrame.setSize(800,600);
        mainFrame.setVisible(true);
        
        mainFrame.pack();
		Thread.sleep(1000);
		wc.simulateForward();
        
        // Start main timer
        this.tick = 100;
        this.timer = new Timer(tick, new TimerActionListener(wc));
        this.timer.start();
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
