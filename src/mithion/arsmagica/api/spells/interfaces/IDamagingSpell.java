package mithion.arsmagica.api.spells.interfaces;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Indicates that the spell deals damage
 * @author Mithion
 *
 */
public interface IDamagingSpell {
	/**
	 * Get the damage dealt by the spell
	 * @param stack The itemstack representing the current spell
	 * @param world The world the spell is being cast in
	 * @param caster The entity casting the spell
	 * @param castingMode The casting mode of the caster
	 * @return The amount of damage to deal
	 */
	public int GetDamage(ItemStack stack, World world, EntityLiving caster, int castingMode);
}
