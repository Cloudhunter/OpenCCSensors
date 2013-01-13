package openccsensors.common.sensors.vanilla;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
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
		// get the basic info
		HashMap map = getBasicInformation(world);
		
		// get the tile entity
		TileEntity entity = world.getBlockTileEntity(xCoord, yCoord, zCoord);
		
		// grab the nbt data from the tile entity
		NBTTagCompound compound = new NBTTagCompound();
		entity.writeToNBT(compound);
		
		// merge the nbt data into the basic info
		map.putAll(NetworkHelper.NBTToMap(compound));
		
		return map;
	}


}
