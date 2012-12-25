package openccsensors.common.api;

import net.minecraft.item.ItemStack;

public interface ISensorCard 
{
	ISensorInterface getSensorInterface(ItemStack itemstack, boolean turtle);
}
