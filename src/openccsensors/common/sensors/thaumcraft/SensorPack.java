package openccsensors.common.sensors.thaumcraft;

import openccsensors.common.SensorInterfaceManager;
import openccsensors.common.sensors.SensorCard;

public class SensorPack {
	
	public static void init() 
	{
		SensorInterfaceManager.registerInterface(new Thaumcraft3SensorInterface());
	}
}	