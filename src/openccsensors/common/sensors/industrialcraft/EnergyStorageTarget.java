package openccsensors.common.sensors.industrialcraft;

import ic2.api.IEnergyStorage;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.common.core.ISensorTarget;
import openccsensors.common.sensors.TileSensorTarget;

public class EnergyStorageTarget extends TileSensorTarget implements ISensorTarget
{

	EnergyStorageTarget(TileEntity targetEntity) {
		super(targetEntity);
	}

	public Map getDetailInformation(World world)
	{
		HashMap retMap = new HashMap();
		
		TileEntity tile = world.getBlockTileEntity(xCoord, yCoord, zCoord);
		if (tile == null || !(tile instanceof IEnergyStorage)) {
			return null;
		}
		
		retMap.put("Stored", ((IEnergyStorage) tile).getStored());
		retMap.put("Capacity", ((IEnergyStorage) tile).getCapacity());
		retMap.put("Output", ((IEnergyStorage) tile).getOutput());
		
		return retMap;
		
	}

}