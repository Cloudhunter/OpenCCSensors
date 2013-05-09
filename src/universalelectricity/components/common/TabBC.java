package universalelectricity.components.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import universalelectricity.components.common.block.BlockBasicMachine;

public class TabBC extends CreativeTabs
{
	public TabBC(int par1, String par2Str)
	{
		super(par1, par2Str);
	}

	public ItemStack getIconItemStack()
	{
		return new ItemStack(BasicComponents.blockMachine, 1, BlockBasicMachine.BATTERY_BOX_METADATA);
	}
}
