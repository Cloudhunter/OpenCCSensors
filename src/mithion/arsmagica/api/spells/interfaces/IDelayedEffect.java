package mithion.arsmagica.api.spells.interfaces;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

/**
 * Series of functions to allow a spell to become a delayed effect.
 * @author Mithion
 *
 */
public interface IDelayedEffect {
	/**
	 * Gets the relevant data required for each delay tick.  Called only upon initialization.
	 * @param caster The caster of the spell
	 * @param world The world that the spell was cast
	 * @param x The x coordinate the spell was cast
	 * @param y The y coordinate the spell was cast
	 * @param z The z coordinate the spell was cast
	 * @param side TODO
	 * @param castingMode TODO
	 * @return A list of objects needed for each tick
	 */
	public List<Object> GetDelayData(EntityLiving caster, World world, int x, int y, int z, int side, int castingMode);
	
	/**
	 * How many ticks between each delay callback	 
	 */
	public int CallRate();
	
	/**
	 * This is the actual callback function, called every CallRate() ticks.	 
	 */
	public boolean ApplyDelayedEffect(EntityLiving caster, World world, List<Object> delayData, int timesCalled);
	
	/**
	 * Should the delayed effect register automatically whenever the spell is cast?  Return false if you want to manually register.
	 */
	public boolean AutoRegister();
}
