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

	protected DevTileTarget(TileEntity targetEntity) {
		super(targetEntity);
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


}
