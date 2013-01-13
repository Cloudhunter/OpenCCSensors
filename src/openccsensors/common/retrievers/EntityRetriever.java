package openccsensors.common.retrievers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.helper.EntityHelper;

public class EntityRetriever {

	ArrayList<IEntityValidatorCallback> targets = new ArrayList<IEntityValidatorCallback>();

	public HashMap<String, ArrayList<ISensorTarget>> getSphere(World world, int sx, int sy, int sz, double radius)
	{
		HashMap<String, ArrayList<ISensorTarget>> map = new HashMap<String, ArrayList<ISensorTarget>>();

		for (Entry<String, Entity> entity : EntityHelper.getEntities(world, sx, sy, sz, radius).entrySet())
		{
			for (IEntityValidatorCallback wrapper : targets) {
				
				double relativeX = entity.getValue().posX - (sx + 0.5);
				double relativeY = entity.getValue().posY - (sy + 0.5);
				double relativeZ = entity.getValue().posZ - (sz + 0.5);
				
				ISensorTarget target = wrapper.getTargetIfValid(entity.getValue(), relativeX, relativeY, relativeZ);
				
				if (target != null)
				{
					String name = entity.getKey();
					
					ArrayList<ISensorTarget> arr = map.get(name);
					
					if (arr == null) {
					
						arr = new ArrayList<ISensorTarget>();
						map.put(name, arr);
					
					}
					
					arr.add(target);
				}
			}
		}
		
		return map;
	}
	

	public void registerCallback(IEntityValidatorCallback wrapper) {
		targets.add(wrapper);
	}
	
}
