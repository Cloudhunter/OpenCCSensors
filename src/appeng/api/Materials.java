package appeng.api;

import net.minecraft.item.ItemStack;

/*
 * Access to stack-able simple items, these include resources... Craftable, ingridents, and a few simple items that don't have log of their own...
 */
public class Materials
{
	// Tech 1
    public static ItemStack matWoodenGear;
    public static ItemStack matCrank;

    // Grinder...
    public static ItemStack matQuartzDust;
    public static ItemStack matIronDust;
    public static ItemStack matGoldDust;
    public static ItemStack matFlour;

    // Tech 3
    public static ItemStack matSilicon;
	public static ItemStack matProcessorBasicUncooked;
	public static ItemStack matProcessorAdvancedUncooked;
	public static ItemStack matProcessorBasic;
	public static ItemStack matProcessorAdvanced;
	
	// ME Construction...
    public static ItemStack matConversionMatrix;
    public static ItemStack matStorageCell;
    public static ItemStack matStorageBlock;
    public static ItemStack matStorageCluster;
    public static ItemStack matWireless;
    public static ItemStack matStorageCellHouseing;
    public static ItemStack matBlankPattern;
	public static ItemStack matStorageSegment;
    
    // World gen...
    public static ItemStack matQuartz;
    public static ItemStack matQuartzFiber;

    // other items, such as upgrades.
    public static ItemStack matWirelessBooster;
}
