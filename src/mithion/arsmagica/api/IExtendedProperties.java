package mithion.arsmagica.api;

import java.util.List;

import mithion.arsmagica.api.spells.ArsMagicaSpellAffinity;

/**
 * Extended properties on EntityLiving used in Ars Magica.
 * @author Mithion
 *
 */
public interface IExtendedProperties {
	public float getCurrentMana();
	public float getMaxMana();
	
	public int getMarkDimension();
	public int getMagicLevel();
	public int getNumSummons();
	public int getCastingMode();
	
	public AMVector3 getMarkLocation();
	public List<Integer> getKnownSpells();
	public ArsMagicaSpellAffinity getAffinity();
	
	public boolean getHasUnlockedAugmented();
	public boolean isMarkSet();
	
	public boolean setSpellKnown(int spellID);
	public boolean unlearnSpell(int spellID);
	public boolean setMagicLevelWithMana(int magicLevel);
	public void setCastingMode(int castingMode);
	public void setCurrentMana(float currentMana);	
	public void setAffinity(ArsMagicaSpellAffinity affinity);
}
