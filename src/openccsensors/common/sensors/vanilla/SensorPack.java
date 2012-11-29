package openccsensors.common.sensors.vanilla;

import openccsensors.OpenCCSensors;

public class SensorPack {
	public static void init() {
		OpenCCSensors.Items.add(new InventorySensorCard(25648));
		OpenCCSensors.Items.add(new ProximitySensorCard(25649));
	}
}
