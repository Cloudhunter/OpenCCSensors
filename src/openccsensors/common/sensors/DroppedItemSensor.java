package openccsensors.common.sensors;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import openccsensors.common.api.IEntityValidatorCallback;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.targets.DroppedItemTarget;

public class DroppedItemSensor extends EntitySensor implements ISensor {

	public DroppedItemSensor() {
		registerCallback(new IEntityValidatorCallback() {
			@Override
			public ISensorTarget getTargetIfValid(Entity entity,
					double relativeX, double relativeY, double relativeZ) {
				if (entity instanceof EntityItem
						&& ((Entity) entity).isEntityAlive()) {
					return new DroppedItemTarget((EntityItem) entity,
							relativeX, relativeY, relativeZ);
				}
				return null;
			}
		});
	}

	@Override
	public String[] getCustomMethods() {
		return null;
	}

	@Override
	public Object[] callCustomMethod() {
		return null;
	}

}
