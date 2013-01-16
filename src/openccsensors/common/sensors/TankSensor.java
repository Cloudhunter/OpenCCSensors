package openccsensors.common.sensors;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.liquids.ITankContainer;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITileEntityValidatorCallback;
import openccsensors.common.sensors.targets.TankTarget;

public class TankSensor extends TileEntitySensor implements ISensor {

	public TankSensor() {
		registerCallback(new ITileEntityValidatorCallback() {

			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity,
					int relativeX, int relativeY, int relativeZ) {
				if (entity instanceof ITankContainer) {
					return new TankTarget(entity, relativeX, relativeY,
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
