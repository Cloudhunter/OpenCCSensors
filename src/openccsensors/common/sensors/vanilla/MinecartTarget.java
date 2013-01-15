package openccsensors.common.sensors.vanilla;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;

public class MinecartTarget implements ISensorTarget {

	private int id;
	private Vec3 relativePos;
	protected String rawType;
	public MinecartTarget(EntityMinecart entity, double relativeX, double relativeY, double relativeZ)
	{
		id = entity.entityId;
		relativePos = Vec3.createVectorHelper(relativeX, relativeY, relativeZ);
		rawType = entity.getEntityName();
	}
	
	@Override
	public Map getBasicInformation(World world) {

		Entity entity = (Entity) world.getEntityByID(id);

		HashMap retMap = new HashMap();

		retMap.put("type", rawType);
		HashMap<String, Double> pos = new HashMap<String, Double>();
		pos.put("X", relativePos.xCoord);
		pos.put("Y", relativePos.yCoord);
		pos.put("Z", relativePos.zCoord);
		retMap.put("Position", pos);

		return retMap;
	}

	@Override
	public Map getDetailInformation(World world) {
		// TODO Auto-generated method stub
		return new HashMap();
	}

	@Override
	public boolean hasGaugePercentage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getGaugePercentage(World world) {
		// TODO Auto-generated method stub
		return 0;
	}

}
