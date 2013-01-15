package openccsensors.common.sensors.targets;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;

public class DroppedItemTarget extends EntityTarget implements ISensorTarget {

	public DroppedItemTarget(Object entity, double relativeX, double relativeY, double relativeZ)
	{
		super((Entity)entity, relativeX, relativeY, relativeZ);
		rawType = ((EntityItem)entity).func_92014_d().getItemName();
	}
	
	
	@Override
	public HashMap getExtendedDetails(World world) {
		EntityItem entity = (EntityItem) world.getEntityByID(id);
		HashMap retMap = new HashMap();
		retMap.putAll(getBasicDetails(world));
		retMap.put("IsBurning", entity.isBurning());
		retMap.put("StackSize", Integer.toString(entity.func_92014_d().stackSize));
		return retMap;
	}


	@Override
	public String[] getTrackablePropertyNames(World world) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getTrackableProperty(World world, String name) {
		// TODO Auto-generated method stub
		return 0;
	}
}
