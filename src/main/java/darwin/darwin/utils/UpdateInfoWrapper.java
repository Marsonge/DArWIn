package darwin.darwin.utils;

import java.util.List;

import darwin.darwin.model.Creature;
import darwin.darwin.model.grid.Tile;

/**
 * Wrapper class for notifying observers with several arguments
 * @author lulu
 *
 */
public class UpdateInfoWrapper {
	private List<Creature> creatureList;
	private List<Creature> deadList;
	//TODO : Supprimer tileList, non utilisé
	public UpdateInfoWrapper (List<Creature> creatureList, List<Creature> deadList){
		this.creatureList = creatureList;
		this.deadList = deadList;
	}

	public List<Creature> getCreatureList() {
		return creatureList;
	}

	public List<Creature> getDeadList() {
		return deadList;
	}
	
	
}
