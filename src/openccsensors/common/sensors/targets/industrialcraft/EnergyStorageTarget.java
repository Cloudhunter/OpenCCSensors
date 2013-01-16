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

		retMap.put("Stored", storage.getStored());
		retMap.put("Capacity", storage.getCapacity());
		retMap.put("Output", storage.getOutput());
		retMap.put("StoredPercentage", (100.0 / storage.getCapacity()) * storage.getStored());
		
		return retMap;
	}

	@Override
	public String[] getTrackablePropertyNames() {
		return new String[] { "StoredPercentage" };
	}


}