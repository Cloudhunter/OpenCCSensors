package openccsensors.common.sensors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import openccsensors.common.api.IEntityValidatorCallback;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.SensorUpgrade;
import openccsensors.common.helper.EntityHelper;

public abstract class BaseEntitySensor {

	ArrayList<IEntityValidatorCallback> validatorCallbacks = new ArrayList<IEntityValidatorCallback>();

	public void registerCallback(IEntityValidatorCallback wrapper) {
		validatorCallbacks.add(wrapper);
	}

	public HashMap<String, ArrayList<ISensorTarget>> getSurroundingTargets(
			World world, int sx, int sy, int sz, SensorUpgrade upgrade) {

		HashMap<String, ArrayList<ISensorTarget>> map = new HashMap<String, ArrayList<ISensorTarget>>();

		for (Entry<String, Entity> entity : EntityHelper.getEntities(world, sx,
				sy, sz, 16.0F).entrySet()) {

			double relativeX = entity.getValue().posX - (sx + 0.5);
			double relativeY = entity.getValue().posY - (sy + 0.5);
			double relativeZ = entity.getValue().posZ - (sz + 0.5);

			for (IEntityValidatorCallback callback : validatorCallbacks) {

				ISensorTarget target = callback.getTargetIfValid(
						entity.getValue(), relativeX, relativeY, relativeZ);

				if (target != null) {

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
}
