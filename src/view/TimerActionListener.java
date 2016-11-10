package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controler.WorldControler;

public class TimerActionListener implements ActionListener{
	
	private WorldControler wc;
	private ViewPanel vp;
	
	public TimerActionListener (WorldControler wc,ViewPanel vp){
		this.wc = wc;
		this.vp = vp;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.wc.simulateForward();
		this.vp.tick();
		this.vp.NbCreatureUpdate(wc.getCountCreature(),wc.getDeadCountCreature());
	}

}
