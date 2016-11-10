package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controler.WorldControler;

public class TimerActionListener implements ActionListener{
	
	private WorldControler wc;
	private SidePanel sp;
	
	public TimerActionListener (WorldControler wc,SidePanel sp){
		this.wc = wc;
		this.sp = sp;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.wc.simulateForward();
		this.sp.tick();
		this.sp.NbCreatureUpdate(wc.getCountCreature(),wc.getDeadCountCreature());
	}

}
