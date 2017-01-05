package darwin.darwin.utils;

import java.util.List;
import java.util.Map;

import darwin.darwin.model.Creature;
import darwin.darwin.view.ViewCreature;

/**
 * Wrapper class for notifying observers with several arguments
 * @author lulu
 *
 */
public class UpdateInfoWrapper {
	private List<Creature> creatureList;
	private List<Creature> deadList;
	private Map<Creature,ViewCreature> creatureMap;

	
	public UpdateInfoWrapper (List<Creature> creatureList, List<Creature> deadList, Map<Creature,ViewCreature> creatureMap){
		this.creatureList = creatureList;
		this.deadList = deadList;
		this.creatureMap = creatureMap;
	}

	public List<Creature> getCreatureList() {
		return creatureList;
	}

	public List<Creature> getDeadList() {
		return deadList;
	}
	
	public Map<Creature,ViewCreature> getCreatureMap(){
		return creatureMap;
	}
	
	
}
