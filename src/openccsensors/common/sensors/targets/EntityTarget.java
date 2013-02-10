package openccsensors.common.sensors.targets;

import java.util.HashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.common.helper.InventoryHelper;

public abstract class EntityTarget {

	protected int id;
	protected Vec3 relativePos;

	public EntityTarget(Entity obj, double relativeX, double relativeY,
			double relativeZ) {
		id = obj.entityId;
		relativePos = Vec3.createVectorHelper(relativeX, relativeY, relativeZ);
	}

	public HashMap getBasicDetails(World world) {

		Entity entity = world.getEntityByID(id);

		HashMap retMap = new HashMap();

		HashMap<String, Double> pos = new HashMap<String, Double>();
		pos.put("X", relativePos.xCoord);
		pos.put("Y", relativePos.yCoord);
		pos.put("Z", relativePos.zCoord);

		if (entity instanceof EntityItem) {
			ItemStack stack = ((EntityItem)entity).getEntityItem();
			retMap.put("Name", InventoryHelper.getNameForItemStack(stack));
			retMap.put("RawName", stack.getItemName());
		}else {
			retMap.put("Name", (entity instanceof EntityPlayer) ? "Player" : entity.getEntityName());
			retMap.put("RawName", entity.getClass().getName());
		}
		retMap.put("Position", pos);

		return retMap;
	}
	

}
