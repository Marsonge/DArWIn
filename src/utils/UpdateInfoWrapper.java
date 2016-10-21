package utils;

import java.util.LinkedList;
import java.util.List;

import model.Creature;
import model.grid.Tile;
import view.ViewTile;

/**
 * Wrapper class for notifying observers with several arguments
 * @author lulu
 *
 */
public class UpdateInfoWrapper {
	private List<Creature> creatureList;
	private List<ViewTile> tileList;
	
	public UpdateInfoWrapper (List<Creature> creatureList, List<ViewTile> tileList){
		this.creatureList = creatureList;
		this.tileList = tileList;
	}

	public List<Creature> getCreatureList() {
		return creatureList;
	}

	public List<ViewTile> getTileList() {
		return tileList;
	}
	
	
}
