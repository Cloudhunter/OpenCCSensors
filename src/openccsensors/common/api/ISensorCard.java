package openccsensors.common.api;

import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.Item;

public interface ISensorCard 
{
	ISensorInterface getSensorInterface(ItemStack itemstack, boolean turtle);
}
