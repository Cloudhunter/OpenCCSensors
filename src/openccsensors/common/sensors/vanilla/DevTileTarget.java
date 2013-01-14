package openccsensors.common.sensors.vanilla;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.helper.NetworkHelper;
import openccsensors.common.sensors.TileSensorTarget;

public class DevTileTarget extends TileSensorTarget implements ISensorTarget {

	protected DevTileTarget(TileEntity targetEntity, int relativeX, int relativeY, int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}

	@Override
	public Map getDetailInformation(World world)
	{
		HashMap map = getBasicInformation(world);
		
		TileEntity entity = world.getBlockTileEntity(xCoord, yCoord, zCoord);
		
		NBTTagCompound compound = new NBTTagCompound();
		entity.writeToNBT(compound);
		
		map.putAll(NetworkHelper.NBTToMap(compound));
		
		return map;
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
