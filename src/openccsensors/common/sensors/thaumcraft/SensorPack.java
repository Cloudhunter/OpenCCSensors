package openccsensors.common.sensors.thaumcraft;

import openccsensors.common.sensors.SensorCard;

public class SensorPack {
	
	public static void init() 
	{
		SensorCard.registerInterface(new Thaumcraft3SensorInterface());
	}
}	