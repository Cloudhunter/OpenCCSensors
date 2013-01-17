package openccsensors.common.sensors;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITileEntityValidatorCallback;
import openccsensors.common.api.SensorUpgradeTier;
import openccsensors.common.sensors.targets.SignPostTarget;

public class SignSensor extends BaseTileEntitySensor implements ISensor {

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
	public String[] getCustomMethods(SensorUpgradeTier upgrade) {
		return null;
	}

	@Override
	public Object callCustomMethod(ISensorAccess sensor, World world, int x, int y, int z, int methodID, Object[] args, SensorUpgradeTier upgrade) {
		return null;
	}

}
