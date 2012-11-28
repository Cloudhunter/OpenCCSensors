package openccsensors.common.sensors.industrialcraft;

import ic2.api.EnergyNet;
import ic2.api.IEnergyConductor;
import ic2.api.IReactor;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.TileSensorTarget;

public class EnergyConductorTarget extends TileSensorTarget implements ISensorTarget{

	protected EnergyConductorTarget(TileEntity targetEntity) {
		super(targetEntity);
	}

	@Override
	public Map getDetailInformation(World world) {
		
		HashMap retMap = new HashMap();
		
		TileEntity conductor = (TileEntity) world.getBlockTileEntity(xCoord, yCoord, zCoord);
		Long energyConducted = EnergyNet.getForWorld(world).getTotalEnergyConducted(conductor);
		
		retMap.put("EnergyConducted", energyConducted);
		
		return retMap;
	}

}
