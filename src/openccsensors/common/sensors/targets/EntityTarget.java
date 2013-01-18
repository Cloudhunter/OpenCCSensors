package openccsensors.common.sensors.targets;

import java.util.HashMap;

import openccsensors.common.helper.SensorHelper;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityTarget {

	protected int id;
	protected Vec3 relativePos;

	public EntityTarget(Entity obj, double relativeX, double relativeY,
			double relativeZ) {
		id = obj.entityId;
		relativePos = Vec3.createVectorHelper(relativeX, relativeY, relativeZ);
	}

	public HashMap getBasicDetails(World world) {

		Entity entityLiving = world.getEntityByID(id);

		HashMap retMap = new HashMap();

		HashMap<String, Double> pos = new HashMap<String, Double>();
		pos.put("X", relativePos.xCoord);
		pos.put("Y", relativePos.yCoord);
		pos.put("Z", relativePos.zCoord);

		retMap.put("Type", getType(entityLiving));
		retMap.put("Position", pos);

		return retMap;
	}
	
	public String getType(Entity e) {
		return SensorHelper.getType(e.getClass().getName());
	}

}
