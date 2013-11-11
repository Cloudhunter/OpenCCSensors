package mithion.arsmagica.api.spells.interfaces;

import net.minecraft.item.ItemStack;

/**
 * Indicates that this spell applies a buff to the target
 * @author Mithion
 *
 */
public interface IBuffingSpell {
	public int[] getBuffIDs(ItemStack stack);
	public int getDuration(ItemStack stack, int buffID, int castingMode);
}
