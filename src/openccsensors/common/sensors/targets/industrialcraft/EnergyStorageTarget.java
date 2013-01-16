package openccsensors.common.sensors.targets.industrialcraft;

import ic2.api.IEnergyStorage;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.targets.TileSensorTarget;

public class EnergyStorageTarget extends TileSensorTarget implements
		ISensorTarget {

	public EnergyStorageTarget(TileEntity targetEntity, int relativeX,
			int relativeY, int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}

	@Override
	public HashMap getExtendedDetails(World world) {
		HashMap retMap = getBasicDetails(world);

		IEnergyStorage storage = (IEnergyStorage) world.getBlockTileEntity(
				xCoord, yCoord, zCoord);
		int capacity = storage.getCapacity();
		int stored = storage.getStored();

		retMap.put("Stored", stored);
		retMap.put("Capacity", capacity);
		retMap.put("Output", storage.getOutput());
		retMap.put("StoredPercentage", 0);
		
		if (capacity > 0) {
			retMap.put("StoredPercentage", Math.max(Math.min(100,(int)((100.0 / capacity) * stored)), 0));
		}
		
		return retMap;
	}

	@Override
	public String[] getTrackablePropertyNames() {
		return new String[] { "StoredPercentage" };
	}


}