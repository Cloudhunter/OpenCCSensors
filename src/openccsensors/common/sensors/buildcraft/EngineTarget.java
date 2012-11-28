package openccsensors.common.sensors.buildcraft;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.common.core.ISensorTarget;
import openccsensors.common.sensors.TileSensorTarget;
import buildcraft.energy.TileEngine;

public class EngineTarget extends TileSensorTarget implements ISensorTarget
{

	EngineTarget(TileEntity targetEntity) {
		super(targetEntity);
	}

	@Override
	public Map getDetailInformation(World world) {

		HashMap retMap = new HashMap();
		
		TileEntity tile = world.getBlockTileEntity(xCoord, yCoord, zCoord);
		if (tile == null || !(tile instanceof TileEngine)) {
			return null;
		}
		
		retMap.put("Active", ((TileEngine) tile).engine.isActive());
		retMap.put("Heat", ((TileEngine) tile).engine.getHeat());
		retMap.put("IsBurning", ((TileEngine) tile).engine.isBurning());
		retMap.put("PistonSpeed", ((TileEngine) tile).engine.getPistonSpeed());
		retMap.put("Energy", ((TileEngine) tile).engine.energy);
		retMap.put("MaxEnergy", ((TileEngine) tile).engine.maxEnergy);
		
		return retMap;
	}

}
