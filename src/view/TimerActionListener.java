package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controler.WorldControler;

public class TimerActionListener implements ActionListener{
	
	private WorldControler wc;
	
	public TimerActionListener (WorldControler wc){
		this.wc = wc;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.wc.simulateForward();		
	}

}
