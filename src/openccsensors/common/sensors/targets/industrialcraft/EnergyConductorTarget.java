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

	public EnergyConductorTarget(TileEntity targetEntity, int relativeX,
			int relativeY, int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}

	@Override
	public HashMap getExtendedDetails(World world) {
		HashMap retMap = getBasicDetails(world);
		TileEntity conductor = world.getBlockTileEntity(xCoord, yCoord, zCoord);
		long emitted = EnergyNet.getForWorld(world).getTotalEnergyEmitted(conductor);
		long sunken = EnergyNet.getForWorld(world).getTotalEnergySunken(conductor);
		retMap.put("EnergyEmitted", emitted);
		retMap.put("EnergySunken", sunken);
		return retMap;
	}

	@Override
	public String[] getTrackablePropertyNames(World world) {
		return null;
	}

	@Override
	public int getTrackableProperty(World world, String name) {
		return 0;
	}


}
