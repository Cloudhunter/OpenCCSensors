package openccsensors.common.sensors.targets;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;

public class DroppedItemTarget implements ISensorTarget {

	private int id;
	private Vec3 relativePos;
	protected String rawType;
	public DroppedItemTarget(Entity entity, double relativeX, double relativeY, double relativeZ)
	{
		id = entity.entityId;
		relativePos = Vec3.createVectorHelper(relativeX, relativeY, relativeZ);
		rawType = ((EntityItem)entity).func_92014_d().getItemName();
	}
	
	private void addPositionToMap(Entity entity, Map map) {
		HashMap<String, Double> pos = new HashMap<String, Double>();
		pos.put("X", relativePos.xCoord);
		pos.put("Y", relativePos.yCoord);
		pos.put("Z", relativePos.zCoord);
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
	
	@Override
	public boolean hasGaugePercentage() {
		return false;
	}

	@Override
	public double getGaugePercentage(World world) {
		return 0;
	}
}
