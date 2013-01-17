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

	public ReactorTarget(TileEntity targetEntity, int relativeX, int relativeY,
			int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}

	@Override
	public HashMap getExtendedDetails(World world) {
		
		HashMap retMap = getBasicDetails(world);
		IReactor reactor = (IReactor) world.getBlockTileEntity(xCoord, yCoord,
				zCoord);

		int maxHeat = reactor.getMaxHeat();
		int heat = reactor.getHeat();
		
		retMap.put("Heat", heat);
		retMap.put("MaxHeat", maxHeat);
		retMap.put("Output", reactor.getOutput() * IC2Reactor.getEUOutput());
		retMap.put("Active", reactor.produceEnergy());
		retMap.put("HeatPercentage", 0);
		if (maxHeat > 0) {
			int heatPercentage = (int)((100.0 / maxHeat) * heat);
			retMap.put("HeatPercentage", Math.max(Math.min(heatPercentage, 100), 0));
		}
		return retMap;
	}

	@Override
	public String[] getTrackablePropertyNames() {
		return new String[] { "HeatPercentage" };
	}




}
