package mithion.arsmagica.api.spells;

import net.minecraft.item.Item;

/**
 * Affinity information
 * @author Mithion
 *
 */
public enum ArsMagicaSpellAffinity {
	NONE(0, -1, null),
	ARCANE(1, 11, null),
	WATER(2, 3, null),
	FIRE(3, 2, null),
	EARTH(4, 7, null),
	AIR(5, 9, null),
	MAGMA(6, 8, null),
	LIGHTNING(7, 4, null),
	ICE(8, 6, null),
	PLANT(9, 5, null),
	LIFE(10, 11, null),
	ENDER(11, 10, null);
	
	public int ID;
	private int opposingAffinity;
	public Item representItem;
	
	private ArsMagicaSpellAffinity(int ID, int opposingAffinity, Item representItem){
		this.ID = ID;
		this.opposingAffinity = opposingAffinity;
		this.representItem = representItem;
	}
	
	public ArsMagicaSpellAffinity getOpposingAffinity(){
		for (ArsMagicaSpellAffinity affinity : ArsMagicaSpellAffinity.values()){
			if (affinity.ID == this.opposingAffinity){
				return affinity;
			}
		}
		return ArsMagicaSpellAffinity.NONE;
	}
	
	public void setRepresentItem(Item representItem){
		if (this.representItem == null){
			this.representItem = representItem;
		}
	}
	
	public static ArsMagicaSpellAffinity getByID(int ID){
		for (ArsMagicaSpellAffinity affinity : ArsMagicaSpellAffinity.values()){
			if (affinity.ID == ID){
				return affinity;
			}
		}
		return ArsMagicaSpellAffinity.NONE;
	}
}
