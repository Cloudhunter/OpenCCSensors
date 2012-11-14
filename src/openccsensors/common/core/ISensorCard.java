package openccsensors.common.core;

import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public interface ISensorCard 
{
	ISensorInterface getSensorInterface(ItemStack itemstack, boolean turtle);	
}
