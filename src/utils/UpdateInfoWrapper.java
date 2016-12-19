package utils;

import java.util.List;

import model.Creature;
import model.grid.Tile;

/**
 * Wrapper class for notifying observers with several arguments
 * @author lulu
 *
 */
public class UpdateInfoWrapper {
	private List<Creature> creatureList;
	private List<Tile> tileList;
	private Creature currentcreature;
	//TODO : Supprimer tileList, non utilisé
	public UpdateInfoWrapper (List<Creature> creatureList, List<Tile> tileList){
		this.creatureList = creatureList;
		this.tileList = tileList;
	}
	
	public UpdateInfoWrapper (Creature currentCreature){
		this.currentcreature = currentCreature;
	}

	public List<Creature> getCreatureList() {
		return creatureList;
	}

	public List<Tile> getTileList() {
		return tileList;
	}
	
	public Creature getCurrentCreature(){
		return currentcreature;
	}
	
	
}
