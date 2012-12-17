package openccsensors.common.sensors.industrialcraft;

import ic2.api.IReactor;
import ic2.api.IC2Reactor;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.TileSensorTarget;

public class ReactorTarget extends TileSensorTarget implements ISensorTarget {

	ReactorTarget(TileEntity targetEntity) {
		super(targetEntity);
	}

	@Override
	public Map getDetailInformation(World world) {
		HashMap retMap = new HashMap();

		IReactor reactor = (IReactor) world.getBlockTileEntity(xCoord, yCoord,
				zCoord);

		retMap.put("Heat", reactor.getHeat());
		retMap.put("MaxHeat", reactor.getMaxHeat());
		retMap.put("Output", reactor.getOutput() * IC2Reactor.getEUOutput());
		retMap.put("Active", reactor.produceEnergy());

		return retMap;

	}

}
