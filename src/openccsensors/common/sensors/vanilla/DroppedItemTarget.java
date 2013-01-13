package openccsensors.common.sensors.vanilla;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;

public class DroppedItemTarget implements ISensorTarget {

	private int id;
	private Vec3 sensorPos;
	protected String rawType;
	public DroppedItemTarget(Entity entity, int sx, int sy, int sz)
	{
		id = entity.entityId;
		sensorPos = Vec3.createVectorHelper(sx, sy, sz);
		rawType = ((EntityItem)entity).func_92014_d().getItemName();
	}
	
	private void addPositionToMap(Entity entity, Map map) {
		HashMap<String, Integer> pos = new HashMap<String, Integer>();
		pos.put("X", ((Double) entity.posX).intValue() - (int) (sensorPos.xCoord + 0.5));
		pos.put("Y", ((Double) entity.posY).intValue() - (int) (sensorPos.yCoord));
		pos.put("Z", ((Double) entity.posZ).intValue() - (int) (sensorPos.zCoord + 0.5));
		map.put("Position", pos);
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
		retMap.put("StackSize", Integer.toString(entity.func_92014_d().stackSize));
		return retMap;
	}
}
