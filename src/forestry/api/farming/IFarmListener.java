package forestry.api.farming;

import java.util.Collection;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public interface IFarmListener {

	/**
	 * Called before a crop is harvested.
	 * @param crop ICrop about to be harvested.
	 * @return true to cancel further processing of this crop.
	 */
	boolean beforeCropHarvest(ICrop crop);
	
	/**
	 * Called after a crop was harvested, but before the result is added to the farm's inventory.
	 * @param harvested Harvested itemstack.
	 * @return true to prevent the farm from storing the crop.
	 */
	boolean beforeProduceStowing(ItemStack produce);
	
	/**
	 * Called after farmland has successfully been cultivated by a farm logic.
	 * @param logic
	 * @param x
	 * @param y
	 * @param z
	 * @param direction
	 * @param extent
	 */
	void hasCultivated(IFarmLogic logic, int x, int y, int z, ForgeDirection direction, int extent);
	
	/**
	 * Called after the stack of harvested crops has been returned by the farm logic, but before it is added to the farm's pending queue. 
	 * @param harvested
	 * @param logic
	 * @param x
	 * @param y
	 * @param z
	 * @param direction
	 * @param extent
	 */
	void hasHarvested(Collection<ICrop> harvested, IFarmLogic logic, int x, int y, int z, ForgeDirection direction, int extent);
	
	/**
	 * Called after the stack of collected items has been returned by the farm logic, but before it is added to the farm's pending queue.
	 * @param collected
	 * @param logic
	 */
	void hasCollected(Collection<ItemStack> collected, IFarmLogic logic);
	
}
