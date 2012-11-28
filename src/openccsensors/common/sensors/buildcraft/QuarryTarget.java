package openccsensors.common.sensors.buildcraft;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.TileSensorTarget;
import buildcraft.energy.TileEngine;
import buildcraft.factory.TileQuarry;

public class QuarryTarget extends TileSensorTarget implements ISensorTarget
{

	QuarryTarget(TileEntity targetEntity) {
		super(targetEntity);
	}

	@Override
	public Map getDetailInformation(World world) {

		HashMap retMap = new HashMap();
		
		TileQuarry quarry = (TileQuarry) world.getBlockTileEntity(xCoord, yCoord, zCoord);
		
		// @TODO: Extract quarry information
		// retMap.put(key, quarry.)
		
		return retMap;
	}

}
