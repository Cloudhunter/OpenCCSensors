package openccsensors.common.sensors.industrialcraft;

import ic2.api.energy.EnergyNet;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.TileSensorTarget;

public class EnergyConductorTarget extends TileSensorTarget implements
		ISensorTarget {

	protected EnergyConductorTarget(TileEntity targetEntity) {
		super(targetEntity);
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

}
