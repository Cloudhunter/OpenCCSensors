package openccsensors.common.sensors;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import openccsensors.common.api.IEntityValidatorCallback;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.sensors.targets.MinecartTarget;

public class MinecartSensor extends BaseEntitySensor implements ISensor {

	public MinecartSensor() {
		registerCallback(new IEntityValidatorCallback(){
			@Override
			public ISensorTarget getTargetIfValid(Entity entity,
					double relativeX, double relativeY, double relativeZ) {
				if (entity instanceof EntityMinecart) {
					return new MinecartTarget(entity, relativeX, relativeY, relativeZ);
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
