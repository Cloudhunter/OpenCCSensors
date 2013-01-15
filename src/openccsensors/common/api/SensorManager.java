package openccsensors.common.api;

import java.util.HashMap;

public class SensorManager {

	private static HashMap<Class, ISensor> registry = new HashMap<Class, ISensor>();
	
	public static ISensor getSensor(Class sensorClass)
	{
		return registry.get(sensorClass);
	}
	
	public static void registerSensor(ISensor sensor)
	{
		registry.put(sensor.getClass(), sensor);
	}
}
