package openccsensors.common.sensors.industrialcraft;

import openccsensors.common.sensors.SensorCard;

public class SensorPack {
	
	public static void init() 
	{
		if (ic2.api.Items.getItem("copperCableItem") != null)
		{
			SensorCard.registerInterface(new IC2SensorInterface());
		}
	}
}