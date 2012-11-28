package openccsensors.common.sensors.industrialcraft;

import ic2.api.IReactor;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.common.core.ISensorTarget;
import openccsensors.common.sensors.TileSensorTarget;


public class ReactorTarget extends TileSensorTarget implements ISensorTarget
{

	ReactorTarget(TileEntity targetEntity) {
		super(targetEntity);
	}


	public Map getDetailInformation(World world)
	{
		HashMap retMap = new HashMap();
		
		TileEntity tile = world.getBlockTileEntity(xCoord, yCoord, zCoord);
		if (tile == null || !(tile instanceof IReactor)) {
			return null;
		}
		
		retMap.put("Heat", ((IReactor) tile).getHeat());
		retMap.put("MaxHeat", ((IReactor) tile).getMaxHeat());
		retMap.put("Output", ((IReactor) tile).getOutput());
		retMap.put("Active", ((IReactor) tile).produceEnergy());
		
		return retMap;
		
	}

}
