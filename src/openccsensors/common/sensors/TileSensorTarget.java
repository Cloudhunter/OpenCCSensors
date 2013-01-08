package openccsensors.common.sensors;

import java.util.HashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.helper.SensorHelper;

public class TileSensorTarget {

	public int xCoord;
	public int yCoord;
	public int zCoord;

	public String rawType;
	
	protected TileSensorTarget(TileEntity targetEntity) {

		xCoord = targetEntity.xCoord;
		yCoord = targetEntity.yCoord;
		zCoord = targetEntity.zCoord;
		
		rawType = targetEntity.getClass().getName();
	}


	public HashMap getBasicInformation(World world) {
		
		HashMap retMap = new HashMap();

		retMap.put("type", SensorHelper.getType(rawType));

		return retMap;
	}
	
}
