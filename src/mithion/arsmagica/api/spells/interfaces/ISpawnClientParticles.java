package mithion.arsmagica.api.spells.interfaces;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Indicates that this spell spawns client particles after applying its effect
 */
public interface ISpawnClientParticles {
	/**
	 * This will be called after a client particle packet is received.  The packet will have already identified that this was the spell cast, so no checks are required.
	 * @param caster
	 * The caster of the spell, local version
	 * @param target
	 * The target of the spell, local version
	 * @param world
	 * The local world
	 * @param x
	 * The x coordinate of the spell's target
	 * @param y
	 * The y coordinate of the spell's target
	 * @param z
	 * The z coordinate of the spell's target
	 * @param particleData
	 * Any data that was returned from the CreateSpawnClientParticlePacketData function
	 */
	public void SpawnClientParticles(EntityLiving caster, EntityLiving target, World world, int x, int y, int z, byte[] particleData);

	/**
	 * This method is to obtain any relevant information about the spell being cast in order to spawn particles on the client's instances.  Client method will get the same parameters as well as the returned byte array.
	 * @param stack The itemstack containing the spell scroll
	 * @param caster The entity that casted the spell
	 * @param target The target (if any) of the spell
	 * @param world The world that the spell was cast in
	 * @param x The x-coordinate of the spell's target
	 * @param y The y-coordinate of the spell's target
	 * @param z The z-coordinate of the spell's target
	 * 
	 * @return An array of bytes containing any pertinent information needed to spawn particles. Decode in the 'SpawnClientParticles' method.
	 */
	public byte[] CreateSpawnClientParticlePacketData(ItemStack stack, EntityLiving caster, EntityLiving target, World world, int x, int y, int z);
}
