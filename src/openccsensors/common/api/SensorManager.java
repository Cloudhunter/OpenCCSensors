package openccsensors.common.api;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class SensorManager {
	
	public static HashMap<Class, ISensor> registry = new HashMap<Class, ISensor>();
	public static HashMap<Class, Icon> iconRegistry = new HashMap<Class, Icon>();

	public static ISensor getSensor(Class sensorClass) {
		return registry.get(sensorClass);
	}
	
	public static Icon getIcon(Class sensorClass) {
		return iconRegistry.get(sensorClass);
	}

	public static void registerSensor(ISensor sensor) {
		registry.put(sensor.getClass(), sensor);
	}

	public static void registerIcons(IconRegister iconRegister) {
		Icon icon;
		for (Entry<Class, ISensor> entry : registry.entrySet()) {
			icon = iconRegister.func_94245_a(entry.getValue().getIconName());
			iconRegistry.put(entry.getKey(), icon);
		}
	}
}
