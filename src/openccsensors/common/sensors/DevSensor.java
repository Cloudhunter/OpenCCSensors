package openccsensors.common.sensors;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITileEntityValidatorCallback;
import openccsensors.common.api.SensorUpgrade;
import openccsensors.common.sensors.targets.DevTileTarget;

public class DevSensor extends BaseTileEntitySensor implements ISensor {

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
	public String[] getCustomMethods(SensorUpgrade upgrade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object callCustomMethod(ISensorAccess sensor, World world, int x, int y, int z, int methodID, Object[] args, SensorUpgrade upgrade) {
		// TODO Auto-generated method stub
		return null;
	}

}
