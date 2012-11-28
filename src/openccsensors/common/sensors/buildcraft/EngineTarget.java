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
		
		TileEngine tileEngine = (TileEngine) world.getBlockTileEntity(xCoord, yCoord, zCoord);
		
		retMap.put("Active", tileEngine.engine.isActive());
		retMap.put("Heat", tileEngine.engine.getHeat());
		retMap.put("IsBurning", tileEngine.engine.isBurning());
		retMap.put("PistonSpeed", tileEngine.engine.getPistonSpeed());
		retMap.put("Energy", tileEngine.engine.energy);
		retMap.put("MaxEnergy", tileEngine.engine.maxEnergy);
		
		return retMap;
	}

}
