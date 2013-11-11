package mithion.arsmagica.api.spells.interfaces;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

/**
 * Indicates that this spell needs special conditions for the AI to cast it.
 * @author Mithion
 *
 */
public interface ISpecialAILogic {
	/**
	 * Callback from the AI that checks to see if the spell should be cast under the current conditions.
	 * @param world  The world the spell is being cast in
	 * @param caster The caster casting the spell
	 * @param target The target of the spell (if any)
	 * @return True to cast the spell, otherwise false.
	 */
	public boolean shouldCast(World world, EntityLiving caster, EntityLiving target);
	
	/**
	 * This is a callback that can be used to perform any actions on the world or surroundings before the AI casts the spell
	 */
	public void PreCastActions(World world, EntityLiving caster, EntityLiving target);
	
	/**
	 * This is a callback that can be used to perform any actions on the world or surroundings after the AI casts the spell
	 */
	public void PostCastActions(World world, EntityLiving caster, EntityLiving target);
}
