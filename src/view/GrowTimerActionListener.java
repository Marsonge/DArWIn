package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controler.WorldControler;

public class GrowTimerActionListener implements ActionListener{
	
	private int time;
	private WorldControler wc;
	
	public GrowTimerActionListener (WorldControler wc){
		this.wc = wc;
		this.time=0;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.wc.grow();
		
	}

}
