package openccsensors.common.api;

import net.minecraft.tileentity.TileEntity;


public interface ITileEntityValidatorCallback {
	public ISensorTarget getTargetIfValid(TileEntity entity, int relativeX, int relativeY, int relativeZ);

}
