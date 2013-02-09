package forestry.api.genetics;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import forestry.api.apiculture.IBreedingManager;

/**
 * Can be used to garner information on bee breeding and to register new bees. See {@link IBreedingManager}
 * 
 * @author SirSengir
 */
public interface IApiaristTracker {

	void decodeFromNBT(NBTTagCompound nbttagcompound);

	void encodeToNBT(NBTTagCompound nbttagcompound);

	String getModeName();

	void setModeName(String name);

	void registerQueen(IIndividual bee);

	int getQueenCount();

	void registerPrincess(IIndividual bee);

	int getPrincessCount();

	void registerDrone(IIndividual bee);

	int getDroneCount();

	/**
	 * @return Integer denoting the amount of species discovered.
	 */
	int getSpeciesBred();

	void registerMutation(IMutation mutation);

	boolean isDiscovered(IMutation mutation);

	/**
	 * Queries the tracker for discovered species.
	 * @param species Species to check.
	 * @return true if the species has been bred.
	 */
	boolean isDiscovered(IAlleleSpecies species);

	/**
	 * Synchronizes the tracker to the client side. Should be called before opening the 
	 * @param world
	 * @param player
	 */
	void synchToPlayer(EntityPlayer player);

}
