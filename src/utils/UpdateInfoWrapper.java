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
	private List<Tile> tileList;
	//TODO : Supprimer tileList, non utilisé
	public UpdateInfoWrapper (List<Creature> creatureList, List<Tile> tileList){
		this.creatureList = creatureList;
		this.tileList = tileList;
	}

	public List<Creature> getCreatureList() {
		return creatureList;
	}

	public List<Tile> getTileList() {
		return tileList;
	}
	
	
}
