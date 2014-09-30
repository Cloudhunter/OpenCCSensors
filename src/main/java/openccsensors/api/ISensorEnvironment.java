package openccsensors.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface ISensorEnvironment {
	
	public int getFacing();
	public ChunkCoordinates getLocation();
	public ItemStack getSensorCardStack();
	public World getWorld();

}