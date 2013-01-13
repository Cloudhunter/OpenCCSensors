package openccsensors.common.sensors.industrialcraft;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.TileSensorTarget;

public class MassFabTarget extends TileSensorTarget implements
		ISensorTarget {

	MassFabTarget(TileEntity targetEntity, int relativeX, int relativeY, int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}

	@Override
	public Map getDetailInformation(World world) {
		
		HashMap retMap = new HashMap();
		
		NBTTagCompound tagCompound = new NBTTagCompound();
		TileEntity machine = (TileEntity) world.getBlockTileEntity(xCoord, yCoord, zCoord);
		machine.writeToNBT(tagCompound);
		retMap.put("PercentComplete", (100.0/IC2SensorInterface.MASSFAB_MAX_ENERGY) * tagCompound.getInteger("energy"));
		retMap.put("Energy", tagCompound.getInteger("energy"));
		return retMap;
		
	}
}
