package openccsensors.common.sensors.targets;

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
	private Vec3 relativePos;
	protected String rawType;

	public LivingTarget(EntityLiving living, double relativeX, double relativeY, double relativeZ) {
		id = living.entityId;
		relativePos = Vec3.createVectorHelper(relativeX, relativeY, relativeZ);
		rawType = (living instanceof EntityPlayer) ? "Player" : living
				.getEntityName();

	}

	private void addPositionToMap(EntityLiving living, Map map) {
		HashMap<String, Double> pos = new HashMap<String, Double>();
		pos.put("X", relativePos.xCoord);
		pos.put("Y", relativePos.yCoord);
		pos.put("Z", relativePos.zCoord);
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

		return retMap;
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