package openccsensors.common.sensors;

import buildcraft.api.power.IPowerReceptor;
import net.minecraft.tileentity.TileEntity;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITileEntityValidatorCallback;
import openccsensors.common.sensors.targets.buildcraft.PowerReceptorTarget;

public class BuildCraftSensor extends TileEntitySensor implements ISensor {

	public BuildCraftSensor() {
		
		registerCallback(new ITileEntityValidatorCallback(){

			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity,
					int relativeX, int relativeY, int relativeZ) {
				if (entity instanceof IPowerReceptor) {
					return new PowerReceptorTarget(entity, relativeX, relativeY, relativeZ);
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
