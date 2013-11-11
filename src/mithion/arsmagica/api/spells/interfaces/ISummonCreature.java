package mithion.arsmagica.api.spells.interfaces;

import net.minecraft.item.ItemStack;

/**
 * Instructs the system to spawn the specified creature class when cast.
 * @author Mithion
 *
 */
public interface ISummonCreature {
	/**
	 * The creature class to spawn.
	 */
	public Class getCreatureClass(ItemStack stack);
	/**
	 * For use with the occulus.
	 * @return The human-readable name of the creature summoned.
	 */
	public String getCreatureName(ItemStack stack);
	/**
	 * The duration the summon will last.
	 * @param castingMode
	 * @return
	 */
	public int GetSummonDuration(ItemStack stack, int castingMode);
}
