package openccsensors.common.sensors.targets;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.helper.EntityHelper;

public class LivingTarget implements ISensorTarget {

	private boolean isValid = false;
	private int id;
	private Vec3 relativePos;
	protected String rawType;

	public LivingTarget(Object obj, double relativeX, double relativeY, double relativeZ) {

		isValid = obj instanceof EntityLiving;
		
		if (isValid) {
			EntityLiving living = (EntityLiving) obj;
			id = living.entityId;
			relativePos = Vec3.createVectorHelper(relativeX, relativeY, relativeZ);
			rawType = (living instanceof EntityPlayer) ? "Player" : living
					.getEntityName();
		}
	}

	@Override
	public HashMap getBasicDetails(World world) {

		return new HashMap();
	}

	@Override
	public HashMap getExtendedDetails(World world) {

		HashMap retMap = new HashMap();

		retMap.putAll(getBasicDetails(world));

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