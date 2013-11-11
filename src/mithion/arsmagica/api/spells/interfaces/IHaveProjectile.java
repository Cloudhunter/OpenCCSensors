package mithion.arsmagica.api.spells.interfaces;

import mithion.arsmagica.api.spells.ProjectileInformation;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * interface to indicate that a spell has a projectile. 
 * @author Mithion
 */
public interface IHaveProjectile{
	
	/**
	 * Projectile movement speed.  Default is 1.0 (for example, arcane bolt).  Talons of the Wind is 2.0.
	 * @param The stack representing the spell.
	 * @return
	 */
	public float ProjectileSpeed(ItemStack stack);
	
	/**
	 * How fast gravity should affect this projectile.
	 * The number is divided by 10.  Negative gravity is possible, 0 for no gravity.
	 */
	public int ProjectileGravity(ItemStack stack);
	
	/**
	 * How many times the projectile should bounce off of blocks.  Will not bounce off of Entities.
	 * A bounce will not apply the effect.
	 */
	public int NumBounces(ItemStack stack);
	
	/**
	 * How many ticks a projectile can live for.  Set to -1 for default.
	 */
	public int ProjectileLife(ItemStack stack);
	
	/**
	 * What does the projectile look like?
	 * @param stack The stack representing the spell.
	 * @return One of the prefab ProjectileInformation classes under ProjectileInformationList, or one you created yourself
	 */
	public ProjectileInformation ProjectileTexture(ItemStack stack);
	public void spawnProjectileTrail(ItemStack stack, World world, double posX, double posY, double posZ);
}
