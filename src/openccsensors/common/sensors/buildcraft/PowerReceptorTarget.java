package openccsensors.common.sensors.buildcraft;

import java.util.HashMap;
import java.util.Map;

import buildcraft.api.power.IPowerReceptor;
import buildcraft.energy.IEngineProvider;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.TileSensorTarget;

public class PowerReceptorTarget extends TileSensorTarget implements ISensorTarget {

	PowerReceptorTarget(TileEntity targetEntity) {
		super(targetEntity);
	}

	@Override
	public Map getDetailInformation(World world) {
		
		HashMap retMap = new HashMap();

		IPowerReceptor tileReceptor = (IPowerReceptor) world
				.getBlockTileEntity(xCoord, yCoord, zCoord);

		retMap.put("Latency", tileReceptor.getPowerProvider().getLatency());
		retMap.put("MinReceived", tileReceptor.getPowerProvider().getMinEnergyReceived());
		retMap.put("MaxReceived", tileReceptor.getPowerProvider().getMaxEnergyReceived());
		retMap.put("Activation", tileReceptor.getPowerProvider().getActivationEnergy());
		retMap.put("MaxStored", tileReceptor.getPowerProvider().getMaxEnergyStored());
		retMap.put("Stored", tileReceptor.getPowerProvider().getEnergyStored());

		return retMap;
	}

}
