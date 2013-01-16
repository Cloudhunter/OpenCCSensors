package openccsensors.common.sensors.targets;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.helper.NetworkHelper;

public class DevTileTarget extends TileSensorTarget implements ISensorTarget {

	public DevTileTarget(TileEntity targetEntity, int relativeX, int relativeY, int relativeZ) {
		super(targetEntity, relativeX, relativeY, relativeZ);
	}

	@Override
	public HashMap getExtendedDetails(World world) {
		TileEntity tile = world.getBlockTileEntity(xCoord, yCoord, zCoord);
		HashMap retMap = getBasicDetails(world);
		retMap.putAll(NetworkHelper.NBTToMap(getTagCompound(tile)));
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
