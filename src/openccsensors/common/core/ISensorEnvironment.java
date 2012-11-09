package openccsensors.common.core;

import net.minecraft.src.ItemStack;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;
import dan200.computer.api.IPeripheral;

public interface ISensorEnvironment 
{
	public World getWorld();
	public Vec3 getLocation(); // Vec3 since that is what turtles use.
	public ItemStack getSensorCard();
}
