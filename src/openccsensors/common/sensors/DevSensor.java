package openccsensors.common.sensors;

import net.minecraft.tileentity.TileEntity;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITileEntityValidatorCallback;
import openccsensors.common.sensors.targets.DevTileTarget;

public class DevSensor extends TileEntitySensor implements ISensor {

	public DevSensor() {
		registerCallback(new ITileEntityValidatorCallback() {

			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity,
					int relativeX, int relativeY, int relativeZ) {
				return new DevTileTarget(entity, relativeX, relativeY,
						relativeZ);
			}

		});
	}

	@Override
	public String[] getCustomMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] callCustomMethod() {
		// TODO Auto-generated method stub
		return null;
	}

}
