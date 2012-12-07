package openccsensors.common.sensors.vanilla;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;
import openccsensors.common.api.ISensorTarget;

public class DroppedItemTarget implements ISensorTarget {

	private int id;
	private Vec3 sensorPos;
	protected String rawType;
	public DroppedItemTarget(Entity entity, int sx, int sy, int sz)
	{
		id = entity.entityId;
		sensorPos = Vec3.createVectorHelper(sx, sy, sz);
		rawType = ((EntityItem)entity).item.getItemName();
	}
	
	@Override
	public Map getBasicInformation(World world) {
		Entity entity = (Entity) world.getEntityByID(id);

		HashMap retMap = new HashMap();

		retMap.put("type", rawType);
		addPositionToMap(entity, retMap);

		return retMap;
	}

	@Override
	public Map getDetailInformation(World world) {
		EntityItem entity = (EntityItem) world.getEntityByID(id);
		HashMap retMap = new HashMap();
		retMap.put("type", rawType);
		retMap.put("IsBurning", entity.isBurning());
		retMap.put("StackSize", Integer.toString(entity.item.stackSize));
		addPositionToMap(entity, retMap);
		return retMap;
	}
	
	private void addPositionToMap(Entity entity, Map map) {
		HashMap<String, Integer> pos = new HashMap<String, Integer>();
		pos.put("X", ((Double) entity.posX).intValue() - (int) sensorPos.xCoord);
		pos.put("Y", ((Double) entity.posY).intValue() - (int) sensorPos.yCoord);
		pos.put("Z", ((Double) entity.posZ).intValue() - (int) sensorPos.zCoord);
		map.put("Position", pos);
	}
}
