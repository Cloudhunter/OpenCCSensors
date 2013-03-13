package appeng.api.me.util;

import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/*
 * Interact with the internals of assembler patterns, get this via Util.getAssemblerPattern(...)
 */
public interface IAssemblerPattern
{
	
	IAssemblerCluster getCluster();
	
    // returns a 3x3 matrix of nulls or ItemStacks, or null if it is not included.
    ItemStack[] getCraftingMatrix();
    
    // same as above, but gets an crafting inventory for real crafting...
    // item pool is optional, null will work, but it won't beable to edit items...
    InventoryCrafting getCraftingInv( World w, IMEInventory itemPool, List<ItemStack> missing, List<ItemStack> used );
    
    // returns a ItemStack, if the pattern is encoded, this will ALWAYS have a value.
    ItemStack getOutput();
    
    // returns true if there is a pattern encoded.
    boolean isEncoded();
    
    // encode a pattern
    // craftingMatrix - accepts a 3x3 grid of ItemStacks and Nulls
    // output - accepts a single ItemStack, NEVER SEND NULL
    void encodePattern(ItemStack[] craftingMatrix, ItemStack output);
    
    /*
     * Validates the Recipe, I have no idea what the World is for, its just part of IRecipe...
     */
    boolean isCraftable(World w);
    
    /*
     * Returns a condensed list of requirements.
     * Example: sticks, will return a single stack of 2, rather then two stacks of 1.
     * The same Item will not show more than one stack.
     */
    public List<ItemStack> condensedRequirements();
    
    // returns the TE for the interface...
	TileEntity getInterface();
	
	// sets the TE for the interface...
	void setInterface(TileEntity a);
}

