package openccsensors.common.sensors;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITileEntityValidatorCallback;
import openccsensors.common.sensors.targets.SignPostTarget;

public class SignSensor extends TileEntitySensor implements ISensor {

	public SignSensor() {
		registerCallback(new ITileEntityValidatorCallback() {

			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity,
					int relativeX, int relativeY, int relativeZ) {
				if (entity instanceof TileEntitySign) {
					return new SignPostTarget(entity, relativeX, relativeY,
							relativeZ);
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
