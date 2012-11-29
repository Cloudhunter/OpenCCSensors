package openccsensors.common.sensors.vanilla;

import cpw.mods.fml.common.registry.GameRegistry;
import openccsensors.OpenCCSensors;
import openccsensors.common.sensors.SensorCard;

public class SensorPack {
	
	public static void init()
	{
		SensorCard.registerInterface(new InventorySensorInterface());
		SensorCard.registerInterface(new ProximitySensorInterface());
	}
}
