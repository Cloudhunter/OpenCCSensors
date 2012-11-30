package openccsensors.common.sensors.vanilla;

import openccsensors.common.sensors.SensorCard;

public class SensorPack {

	public static void init() {
		SensorCard.registerInterface(new InventorySensorInterface());
		SensorCard.registerInterface(new ProximitySensorInterface());
		SensorCard.registerInterface(new LiquidSensorInterface());
		SensorCard.registerInterface(new WorldSensorInterface());
	}
}
