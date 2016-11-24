package utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controler.WorldControler;

public class GrowTimerActionListener implements ActionListener{
	
	private WorldControler wc;
	
	public GrowTimerActionListener (WorldControler wc){
		this.wc = wc;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.wc.grow();
		
	}

}
