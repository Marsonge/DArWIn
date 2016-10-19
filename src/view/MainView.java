package view;

import java.awt.Color;
import javax.swing.JFrame;
import controler.WorldControler;

public class MainView extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Color black = new Color(0,0,0);
	
	/**
	 * MainView()
	 * 
	 * Create the main JFrame and add the ViewGrid 
	 * 
	 */
	public MainView(){
		
		// Create the main JFrame
		JFrame mainFrame = new JFrame("Darwin : ARtificial Wildlife Intelligence");
		
		// Create the grid
		WorldControler wc = new WorldControler(100,8,(float)10000,0,60); 
        ViewGrid vG = new ViewGrid(wc);
        vG.setSize(500, 500);
        
        // Adding viewGrid to mainFrame
        mainFrame.getContentPane().add(vG);
        
        // main JFrame setting
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBackground(black);
        mainFrame.setSize(800,600);
        mainFrame.setVisible(true);
        mainFrame.pack();
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
