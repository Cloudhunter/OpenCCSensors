package mithion.arsmagica.api.spells.interfaces;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Indicates that a spell is channeled
 */
public interface IChanneledSpell{
	/**
	 * The sound to loop while channeling
	 */
	public String ChanneledLoopSound();
	
	/**
	 * Method where you can check whether or not to play the channeled sound.
	 * @param caster
	 * The caster of the spell (who is currently channeling)
	 * @param world
	 * The world the spell is being cast in
	 * @param ticksChanneled
	 * The number of ticks the spell has been channeled so far
	 * @return
	 * True if the sound should be played.
	 */
	public boolean shouldPlayChannellingSound(EntityLiving caster, World world, int ticksChanneled);
	
	/**
	 * Apply the channeled effect.  This is called instead of ApplyEffectEx/ApplyEffectToEntityEx.
	 * @param stack The itemstack representing the spell
	 * @param caster The caster of the spell
	 * @param world The world the spell is being cast in
	 * @param ticksChanneled The number of ticks the spell has been channeled for
	 * @return The mana cost to channel this spell for this tick.
	 */
	public float ApplyChanneledEffect(ItemStack stack, EntityLiving caster, World world, int ticksChanneled);
}
