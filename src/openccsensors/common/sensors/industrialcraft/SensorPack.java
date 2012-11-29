package openccsensors.common.sensors.industrialcraft;

import openccsensors.OpenCCSensors;

public class SensorPack {
	public static void init() {
		OpenCCSensors.Items.add(new IC2SensorCard(25650));
	}
}
