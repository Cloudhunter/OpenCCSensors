package openccsensors.common.sensors.targets.industrialcraft;

import ic2.api.energy.EnergyNet;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.targets.TileSensorTarget;

public class EnergyConductorTarget extends TileSensorTarget implements
		ISensorTarget {

	public EnergyConductorTarget(TileEntity targetEntity, int relativeX, int relativeY, int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}

	@Override
	public Map getDetailInformation(World world) {

		HashMap retMap = new HashMap();

		TileEntity conductor = world.getBlockTileEntity(xCoord, yCoord, zCoord);
		Long energyConducted = EnergyNet.getForWorld(world)
				.getTotalEnergyConducted(conductor);

		retMap.put("EnergyConducted", energyConducted);

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
