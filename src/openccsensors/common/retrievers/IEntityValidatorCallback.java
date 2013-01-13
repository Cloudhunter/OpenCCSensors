package openccsensors.common.retrievers;

import net.minecraft.entity.Entity;
import openccsensors.common.api.ISensorTarget;

public interface IEntityValidatorCallback {
	public ISensorTarget getTargetIfValid(Entity entity, double relativeX, double relativeY, double relativeZ, int x, int y, int z);
}
