package openccsensors.common.core;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface ISensorEnvironment 
{
	public int getFacing();
	public Vec3 getLocation();
	public ItemStack getSensorCard();
	public World getWorld();
}
