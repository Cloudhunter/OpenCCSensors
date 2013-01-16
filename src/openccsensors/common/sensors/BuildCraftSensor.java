package openccsensors.common.sensors;

import buildcraft.api.power.IPowerReceptor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITileEntityValidatorCallback;
import openccsensors.common.api.SensorUpgrade;
import openccsensors.common.sensors.targets.buildcraft.PowerReceptorTarget;

public class BuildCraftSensor extends BaseTileEntitySensor implements ISensor {

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
	public String[] getCustomMethods(SensorUpgrade upgrade) {
		return null;
	}

	@Override
	public Object callCustomMethod(ISensorAccess sensor, World world, int x, int y, int z, int methodID, Object[] args, SensorUpgrade upgrade) {
		return null;
	}

}
