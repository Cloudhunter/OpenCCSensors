package openccsensors.common.sensors;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import openccsensors.common.api.IEntityValidatorCallback;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.SensorUpgrade;
import openccsensors.common.sensors.targets.LivingTarget;

public class ProximitySensor extends EntitySensor implements ISensor {

	public ProximitySensor()
	{
		registerCallback(new IEntityValidatorCallback() {
			@Override
			public ISensorTarget getTargetIfValid(Entity entity, double relativeX, double relativeY, double relativeZ) {
				if (entity instanceof EntityLiving)
				{
					return new LivingTarget((EntityLiving) entity, relativeX, relativeY, relativeZ);
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
