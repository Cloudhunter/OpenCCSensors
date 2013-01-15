package openccsensors.common;

import java.util.HashMap;

import openccsensors.common.api.ISensorInterface;

/**********************************
 * 
 * Id | card
 * 16: Inventory
 * 17: Proximity
 * 18: IC2:
 * 19: Buildcraft
 * 20: Liquid
 * 21: World
 * 22: Dropped item
 * 23: Sign
 * 24: Thaumcraft
 * 25: Minecart
 * 99: Dev
 *
 ************************************
 */

public class SensorInterfaceManager {

	public static HashMap<Integer, ISensorInterface> Interfaces = new HashMap<Integer, ISensorInterface>();
	
	public static void registerInterface(ISensorInterface iface)
	{
		Interfaces.put(iface.getId(), iface);
	}
}
