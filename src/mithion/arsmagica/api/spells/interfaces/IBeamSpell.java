package mithion.arsmagica.api.spells.interfaces;

import mithion.arsmagica.api.particles.IBeamParticle;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Marks the specified spell as a beam spell
 * @author Mithion
 *
 */
public interface IBeamSpell extends IChanneledSpell{
	/**
	 * Gets the range, in blocks, of the beam.
	 * @param caster The entity casting the spell
	 * @param stack The stack representing the beam spell
	 */
	public double GetBeamRange(EntityLiving caster, ItemStack stack);
	
	/**
	 * Callback that will allow certain properties of the beam particle to be set
	 * @param beam The beam on which you can set properties
	 * @param caster The caster of the spell
	 * @param world The world in which the spell is being cast
	 * @param stack The stack represent
	 */
	public void SetBeamProperties(IBeamParticle beam, EntityLiving caster, World world, ItemStack stack);	
}
