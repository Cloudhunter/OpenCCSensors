package am2.api.power;
import net.minecraft.tileentity.TileEntity;

public interface IPowerIntegration <T extends TileEntity> {
	/**
	 * Gets the current charge in the power block
	 */
	public float getCharge();
	
	/**
	 * Gets the current capacity in the power block
	 */
	public float getCapacity();
	
	/**
	 * Gets the current deficit
	 */
	public float getDefecit();
	
	/**
	 * Gets the deficit threshold at which point the block will explode
	 */
	public float getDefecitThreshold();
	
	/**
	 * Can this block provide power?
	 */
	public boolean canProvidePower();
	
	/**
	 * Is this block a nexus?
	 */
	public boolean isNexus();
	
	/**
	 * Is this block a nexus impersonator (Mana Battery is an example)
	 */
	public boolean isNexusImpersonator();
	
	/**
	 * Does this block have a path back to a nexus?
	 */
	public boolean hasNexusPath();
	
	/**
	 * How many hops does the power need to get to the nexus?
	 */
	public int getNumHopsToNexus();
	
	/**
	 * How fast does this block charge?
	 */
	public int getChargeRate();
	
	/**
	 * Gets the next block in the chain back to the nexus.
	 */
	public IPowerIntegration getNextHop();
	
	/**
	 * Gets the current power type of the block.
	 */
	public PowerTypes getPowerType();
}
