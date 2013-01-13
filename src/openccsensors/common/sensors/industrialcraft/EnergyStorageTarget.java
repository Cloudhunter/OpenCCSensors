package openccsensors.common.sensors.industrialcraft;

import ic2.api.IEnergyStorage;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.TileSensorTarget;

public class EnergyStorageTarget extends TileSensorTarget implements
		ISensorTarget {

	EnergyStorageTarget(TileEntity targetEntity) {
		super(targetEntity);
	}

	@Override
	public Map getDetailInformation(World world) {
		HashMap retMap = new HashMap();

		IEnergyStorage storage = (IEnergyStorage) world.getBlockTileEntity(
				xCoord, yCoord, zCoord);

		retMap.put("Stored", storage.getStored());
		retMap.put("Capacity", storage.getCapacity());
		retMap.put("Output", storage.getOutput());

		return retMap;

	}

}