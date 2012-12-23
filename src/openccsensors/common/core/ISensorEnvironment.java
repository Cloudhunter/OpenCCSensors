package openccsensors.common.core;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import dan200.computer.api.IPeripheral;

public interface ISensorEnvironment 
{
	public World getWorld();
	public Vec3 getLocation();
	public int getFacing();
	public ItemStack getSensorCard();
}
