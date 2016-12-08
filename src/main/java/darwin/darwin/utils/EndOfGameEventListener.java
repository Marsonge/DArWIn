package darwin.darwin.utils;

import java.util.EventListener;

public interface EndOfGameEventListener extends EventListener {

	public void actionPerformed(EndOfGameEvent evt);
	
}
