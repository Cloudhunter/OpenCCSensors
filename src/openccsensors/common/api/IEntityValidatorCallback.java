package openccsensors.common.api;

import net.minecraft.entity.Entity;

public interface IEntityValidatorCallback {
	public ISensorTarget getTargetIfValid(Entity entity, double relativeX,
			double relativeY, double relativeZ);
}