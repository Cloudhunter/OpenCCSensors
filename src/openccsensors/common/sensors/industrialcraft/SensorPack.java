package openccsensors.common.sensors.industrialcraft;

import openccsensors.OpenCCSensors;
import openccsensors.common.sensors.SensorCard;

public class SensorPack {
	public static void init()
	{
		SensorCard.registerInterface(new IC2SensorInterface());
	}
}
