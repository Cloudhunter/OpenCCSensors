package openccsensors.common.api;

import java.util.HashMap;

import net.minecraft.world.World;

public interface ISensorTarget {
	public HashMap getBasicDetails(World world);

	public HashMap getExtendedDetails(World world);

	public String[] getTrackablePropertyNames(World world);

	public int getTrackableProperty(World world, String name);
}
