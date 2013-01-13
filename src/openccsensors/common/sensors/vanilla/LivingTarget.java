package openccsensors.common.sensors.vanilla;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.helper.EntityHelper;

/*
 * Object to represent a living target
 */
public class LivingTarget implements ISensorTarget {
	private int id;
	private Vec3 sensorPos;
	protected String rawType;

	LivingTarget(EntityLiving living, int sx, int sy, int sz) {
		id = living.entityId;
		sensorPos = Vec3.createVectorHelper(sx, sy, sz);
		rawType = (living instanceof EntityPlayer) ? "Player" : living
				.getEntityName();

	}

	private void addPositionToMap(EntityLiving living, Map map) {
		HashMap<String, Integer> pos = new HashMap<String, Integer>();
		pos.put("X", ((Double) living.posX).intValue() - (int) (sensorPos.xCoord + 0.5));
		pos.put("Y", ((Double) living.posY).intValue() - (int) (sensorPos.yCoord));
		pos.put("Z", ((Double) living.posZ).intValue() - (int) (sensorPos.zCoord + 0.5));
		map.put("Position", pos);
	}

	@Override
	public Map getBasicInformation(World world) {
		EntityLiving entityLiving = (EntityLiving) world.getEntityByID(id);

		HashMap retMap = new HashMap();

		retMap.put("type", rawType);
		addPositionToMap(entityLiving, retMap);

		return retMap;
	}

	@Override
	public Map getDetailInformation(World world) {
		EntityLiving entityLiving = (EntityLiving) world.getEntityByID(id);

		Map retMap = EntityHelper.livingToMap(entityLiving);

		if (entityLiving == null) {
			return null;
		}

		retMap.put("type", rawType);

		return retMap;
	}

}