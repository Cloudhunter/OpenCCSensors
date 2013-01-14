package openccsensors.common;

import java.util.HashMap;

import openccsensors.common.api.ISensorInterface;

public class SensorInterfaceManager {

	public static HashMap<Integer, ISensorInterface> Interfaces = new HashMap<Integer, ISensorInterface>();
	
	public static void registerInterface(ISensorInterface iface)
	{
		Interfaces.put(iface.getId(), iface);
	}
}
