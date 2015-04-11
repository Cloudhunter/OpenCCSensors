package appeng.api.networking.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import appeng.api.implementations.ICraftingPatternItem;
import appeng.api.storage.data.IAEItemStack;

/**
 * do not implement provided by {@link ICraftingPatternItem}
 * 
 * caching this instance will increase preformance of validation and checks.
 */
public interface ICraftingPatternDetails
{

	/**
	 * @return source item.
	 */
	ItemStack getPattern();

	/**
	 * @param slotIndex
	 * @param itemStack
	 * @param world
	 * 
	 * @return if an item can be used in the specific slot for this pattern.
	 */
	boolean isValidItemForSlot(int slotIndex, ItemStack itemStack, World world);

	/**
	 * @return if this pattern is a crafting pattern ( work bench )
	 */
	boolean isCraftable();

	/**
	 * @return a list of the inputs, will include nulls.
	 */
	IAEItemStack[] getInputs();

	/**
	 * @return a list of the inputs, will be clean
	 */
	IAEItemStack[] getCondencedInputs();

	/**
	 * @return a list of the outputs, will be clean
	 */
	IAEItemStack[] getCondencedOutputs();

	/**
	 * @return a list of the outputs, will include nulls.
	 */
	IAEItemStack[] getOutputs();

	/**
	 * @return if this pattern is enabled to support substitutions.
	 */
	boolean canSubstitute();

	/**
	 * Allow using this instance of the pattern details to preform the crafting action with preformance enhancements.
	 * 
	 * @param craftingInv
	 * @param world
	 * @return the crafted ( work bench ) item.
	 */
	ItemStack getOutput(InventoryCrafting craftingInv, World world);

	/**
	 * Set the priority the of this pattern.
	 * 
	 * @param priority
	 */
	void setPriority(int priority);

}
