package openccsensors.common.sensors.targets;

import java.util.HashMap;

import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;

public class WorldTarget implements ISensorTarget {

	private int currentX;
	private int currentY;
	private int currentZ;
	
	public WorldTarget(int x, int y, int z) {
		currentX = x;
		currentY = y;
		currentZ = z;
	}
	
	@Override
	public HashMap getBasicDetails(World world) {
		HashMap retMap = new HashMap();
		retMap.put("Type",  "World");
		return retMap;
	}

	@Override
	public HashMap getExtendedDetails(World world) {
		HashMap retMap = getBasicDetails(world);
		retMap.put("Dimension", world.getWorldInfo().getDimension());
		retMap.put("Biome", world.getBiomeGenForCoords(currentX, currentZ).biomeName);
		retMap.put("LightLevel", world.getBlockLightValue(currentX, currentY, currentZ));
		retMap.put("Raining", world.isRaining());
		retMap.put("Thundering", world.isThundering());
		retMap.put("Daytime", world.isDaytime());
		return retMap;
	}

	@Override
	public String[] getTrackablePropertyNames(World world) {
		return null;
	}

	@Override
	public int getTrackableProperty(World world, String name) {
		return 0;
	}

}
