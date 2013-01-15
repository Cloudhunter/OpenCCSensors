package openccsensors.common.sensors.targets.industrialcraft;

import ic2.api.IC2Reactor;
import ic2.api.IReactor;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.targets.TileSensorTarget;

public class ReactorTarget extends TileSensorTarget implements ISensorTarget {

	public ReactorTarget(TileEntity targetEntity, int relativeX, int relativeY, int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
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
	
	@Override
	public boolean hasGaugePercentage() {
		return false;
	}

	@Override
	public double getGaugePercentage(World world) {
		return 0;
	}

}
