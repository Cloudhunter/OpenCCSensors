package openccsensors.common.sensors.vanilla;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;
import openccsensors.common.core.ISensorTarget;
import openccsensors.common.helper.LivingEntityHelper;

/*
 * Object to represent a living target
 */
public class LivingTarget implements ISensorTarget
{
	private int id;
	private Vec3 sensorPos;
	protected String rawType;
	
	LivingTarget(EntityLiving living, int sx, int sy, int sz)
	{
		super();
		id = living.entityId;
		sensorPos = Vec3.createVectorHelper(sx, sy, sz);
		rawType = (living instanceof EntityPlayer) ? "Player" : living.getEntityName();

	}
	
	public Map getBasicInformation(World world)
	{
		EntityLiving entityLiving = (EntityLiving)world.getEntityByID(id);
		
		HashMap retMap = new HashMap();
		
		retMap.put("type", rawType);
		addPositionToMap(entityLiving, retMap);
		
		return retMap;
	}
	
	public Map getDetailInformation(World world)
	{
		EntityLiving entityLiving = (EntityLiving)world.getEntityByID(id);
		
		Map retMap = LivingEntityHelper.livingToMap(entityLiving);
		
		if (entityLiving == null)
		{
			return null;
		}
		
		retMap.put("type", rawType);
		addPositionToMap(entityLiving, retMap);
		
		return retMap;
	}
	
	private void addPositionToMap(EntityLiving living, Map map)
	{
		HashMap<String, Integer> pos = new HashMap<String,Integer>();
		pos.put("x", ((Double) living.posX).intValue() - (int) sensorPos.xCoord);
		pos.put("y", ((Double) living.posY).intValue() - (int) sensorPos.yCoord);
		pos.put("z", ((Double) living.posZ).intValue() - (int) sensorPos.zCoord);
		map.put("position", pos);
	}

}