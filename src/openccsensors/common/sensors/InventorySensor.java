package openccsensors.common.sensors;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITileEntityValidatorCallback;
import openccsensors.common.sensors.targets.InventoryTarget;

public class InventorySensor extends TileEntitySensor implements ISensor {

	public InventorySensor()
	{
		registerCallback(new ITileEntityValidatorCallback() {

			@Override
			public ISensorTarget getTargetIfValid(TileEntity entity,
					int relativeX, int relativeY, int relativeZ) {
				if (entity instanceof IInventory)
				{
					return new InventoryTarget(entity, relativeX, relativeY, relativeZ);
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
