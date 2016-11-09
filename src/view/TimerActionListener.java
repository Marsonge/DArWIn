package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controler.WorldControler;

public class TimerActionListener implements ActionListener{
	
	private int time;
	private WorldControler wc;
	private ViewPanel vp;
	
	public TimerActionListener (WorldControler wc,ViewPanel vp){
		this.wc = wc;
		this.vp = vp;
		this.time=0;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("TICK");
		time++;
		this.wc.simulateForward();
		this.vp.tick();
		this.vp.NbCreatureUpdate(wc.getCountCreature());
	}

}
