package openccsensors.common.retrievers;

import net.minecraft.tileentity.TileEntity;
import openccsensors.common.api.ISensorTarget;

public interface ITileEntityValidatorCallback {
	public ISensorTarget getTargetIfValid(TileEntity entity, int relativeX, int relativeY, int relativeZ, int x, int y, int z);

}
