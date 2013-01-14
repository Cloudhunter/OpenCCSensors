package openccsensors.common.sensors.vanilla;

import openccsensors.common.SensorInterfaceManager;
import openccsensors.common.sensors.SensorCard;

public class SensorPack {

	public static void init() {
		SensorInterfaceManager.registerInterface(new InventorySensorInterface());
		SensorInterfaceManager.registerInterface(new ProximitySensorInterface());
		SensorInterfaceManager.registerInterface(new LiquidSensorInterface());
		SensorInterfaceManager.registerInterface(new WorldSensorInterface());
		SensorInterfaceManager.registerInterface(new DroppedItemSensorInterface());
		SensorInterfaceManager.registerInterface(new SignSensorInterface());
		SensorInterfaceManager.registerInterface(new DevSensorInterface());
	}
}
