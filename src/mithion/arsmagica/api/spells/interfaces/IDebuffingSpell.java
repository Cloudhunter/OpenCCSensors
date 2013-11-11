package mithion.arsmagica.api.spells.interfaces;

import net.minecraft.item.ItemStack;

/**
 * Indicates that this spell applies a debuff to the target
 * @author Mithion
 *
 */
public interface IDebuffingSpell {
	public int[] getDebuffIDs(ItemStack stack);
	public int getDuration(ItemStack stack, int buffID, int castingMode);
}
