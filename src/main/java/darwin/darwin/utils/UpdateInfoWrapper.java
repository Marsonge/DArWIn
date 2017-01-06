package darwin.darwin.utils;

import java.util.Collection;
import java.util.List;

import darwin.darwin.view.ViewCreature;

/**
 * Wrapper class for notifying observers with several arguments
 * @author lulu
 *
 */
public class UpdateInfoWrapper {
	private List<ViewCreature> deadList;
	private Collection<ViewCreature> creatureList;

	
	public UpdateInfoWrapper (List<ViewCreature> deadList, Collection<ViewCreature> creatureList){
		this.creatureList = creatureList;
		this.deadList = deadList;
	}

	public Collection<ViewCreature> getCreatureList() {
		return creatureList;
	}

	public List<ViewCreature> getDeadList() {
		return deadList;
	}

	
	
}
