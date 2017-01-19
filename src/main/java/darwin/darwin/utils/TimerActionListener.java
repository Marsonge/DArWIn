package darwin.darwin.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import darwin.darwin.controler.WorldControler;
import darwin.darwin.view.sidepanel.SidePanel;

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
		this.sp.updateNbCreature(wc.getCountCreature(),wc.getDeadCountCreature());
		this.sp.updateCurrentCreature(wc.getCurrentCreature());
	}

}
