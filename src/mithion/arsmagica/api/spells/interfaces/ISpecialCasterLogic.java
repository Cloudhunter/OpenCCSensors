package mithion.arsmagica.api.spells.interfaces;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Indicates that this spell needs special logic for casters that is different than players.  
 * For example, telekinesis putting items into a chest instead of the caster's inventory.
 * @author Mithion
 *
 */
public interface ISpecialCasterLogic {
	/**
	 * Apply the effect when cast by a caster and striking ground
	 * @param stack The stack representing the caster
	 * @param caster The caster of the spell (will be a dummy caster.  Mana will represent the charge level of the caster itself)
	 * @param world The world the spell is being cast in
	 * @param x The x coordinate of the spell effect (NOT the caster!)
	 * @param y The y coordinate of the spell effect (NOT the caster!)
	 * @param z The z coordinate of the spell effect (NOT the caster!)
	 * @param l The side hit
	 * @param castingMode The highest level focus present in the caster
	 * @return True if the effect succeeded, false if it didn't.
	 */
	public boolean CasterApplyEffect(ItemStack stack, EntityLiving caster, World world, int x, int y, int z, int l, int castingMode);
	
	/**
	 * Apply the effect when cast by a caster and striking a living entity
	 * @param stack The stack representing the caster
	 * @param caster The caster of the spell (will be a dummy caster.  Mana will represent the charge level of the caster itself)
	 * @param world The world the spell is being cast in
	 * @param target The target of the spell
	 * @param castingMode The highest level focus present in the caster
	 * @return True if the effect succeeded, false if it didn't.
	 */
	public boolean CasterApplyEffectToEntity(ItemStack stack, EntityLiving caster, World world, EntityLiving target, int castingMode);
	
	/**
	 * Apply the channeled effect when cast by a caster.  Also called with beam spells.
	 * @param stack The stack representing the caster
	 * @param caster The caster of the spell (will be a dummy caster.  Mana will represent the charge level of the caster itself)
	 * @param world The world the spell is being cast in
	 * @param ticksChanneled The number of ticks that the spell has been channeled.
	 * @return True if the effect succeeded, false if it didn't.
	 */
	public float CasterApplyChanneledEffect(ItemStack stack, EntityLiving caster, World world, int ticksChanneled);
}
