package openccsensors.common.api;

import net.minecraft.entity.Entity;
import openccsensors.common.api.ISensorTarget;

public interface IEntityValidatorCallback {
	public ISensorTarget getTargetIfValid(Entity entity, double relativeX, double relativeY, double relativeZ);
}