package openccsensors.common.sensors;

import java.util.HashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.helper.SensorHelper;

public class TileSensorTarget {

	protected int xCoord;
	protected int yCoord;
	protected int zCoord;

	protected String rawType;
	
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
