package openccsensors.common.sensors.targets;

import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.helper.SensorHelper;

public class TileSensorTarget {

	public int xCoord;
	public int yCoord;
	public int zCoord;
	
	public int relativeX;
	public int relativeY;
	public int relativeZ;

	public String rawType;
	
	protected TileSensorTarget(TileEntity targetEntity, int relativeX, int relativeY, int relativeZ) {

		xCoord = targetEntity.xCoord;
		yCoord = targetEntity.yCoord;
		zCoord = targetEntity.zCoord;
		
		this.relativeX = relativeX;
		this.relativeY = relativeY;
		this.relativeZ = relativeZ;
		
		rawType = targetEntity.getClass().getName();
	}


	public HashMap getBasicDetails(World world) {
		
		HashMap retMap = new HashMap();

		HashMap<String, Integer> pos = new HashMap<String, Integer>();
		pos.put("X", relativeX);
		pos.put("Y", relativeY);
		pos.put("Z", relativeZ);

		retMap.put("Type", SensorHelper.getType(rawType));
		retMap.put("Position", pos);
		

		return retMap;
	}
	
	protected NBTTagCompound getTagCompound(TileEntity tile)
	{
		NBTTagCompound tagCompound = new NBTTagCompound();
		tile.writeToNBT(tagCompound);
		return tagCompound;
	}
}
