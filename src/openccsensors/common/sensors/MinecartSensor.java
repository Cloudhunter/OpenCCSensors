package openccsensors.common.sensors;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;
import openccsensors.common.api.IEntityValidatorCallback;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.SensorUpgradeTier;
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
	public String[] getCustomMethods(SensorUpgradeTier upgrade) {
		return null;
	}

	@Override
	public Object callCustomMethod(ISensorAccess sensor, World world, int x, int y, int z, int methodID, Object[] args, SensorUpgradeTier upgrade) {
		return null;
	}

	@Override
	public String getIconName() {
		return "openccsensors:minecart";
	}

}
