package thaumcraft.api.aspects;

import net.minecraftforge.common.ForgeDirection;

/**
 * @author Azanor
 * This interface is used by tiles that use or transport vis. 
 * Only tiles that implement this interface will be able to connect to vis conduits or other thaumic devices
 */
public interface IEssentiaTransport {
	/**
	 * Is this tile able to connect to other vis users/sources on the specified side?
	 * @param face
	 * @return
	 */
	public boolean isConnectable(ForgeDirection face);
	
	/**
	 * Is this side used to input essentia?
	 * @param face
	 * @return
	 */
	boolean canInputFrom(ForgeDirection face);
	
	/**
	 * Is this side used to output essentia?
	 * @param face
	 * @return
	 */
	boolean canOutputTo(ForgeDirection face);
	
//	/**
//	 * Can this tile act as a source of vis?
//	 * @return
//	 */
//	public boolean isVisSource();
//	
//	/**
//	 * Is this tile a conduit that transports vis?
//	 * @return
//	 */
//	public boolean isVisConduit();
		
	/**
	 * Sets the amount of vis and taint TCB this block will apply
	 * @param suction
	 */
	public void setSuction(AspectList suction);
	
	/**
	 * Sets the amount of vis and taint TCB this block will apply
	 * @param suction
	 */
	public void setSuction(Aspect aspect, int amount);
	
	/**
	 * Returns the amount of vis and taint suction this block is applying. Usually should return the highest value of the two
	 * @param loc
	 * 		the location from where the suction is being checked
	 * @return
	 */
	public AspectList getSuction(ForgeDirection face);

	
	/**
	 * remove the specified amount of vis from this transport tile
	 * @param suction
	 * @return how much was actually taken
	 */
	public int takeVis(Aspect aspect, int amount);

	public AspectList getEssentia(ForgeDirection face);

	/**
	 * Essentia will not be drawn from this container unless the suction exceeds this amount.
	 * @return the amount
	 */
	public int getMinimumSuction();

	/**
	 * Return true if you want the conduit to extend a little further into the block. 
	 * Used by jars and alembics that have smaller than normal hitboxes
	 * @return
	 */
	boolean renderExtendedTube();

	
	
}

