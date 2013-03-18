package openccsensors.common.util;

import java.util.HashMap;
import java.util.Map;

import universalelectricity.core.block.IConductor;
import universalelectricity.core.block.IConnector;
import universalelectricity.core.block.IElectricityStorage;
import universalelectricity.core.block.IVoltage;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class UniversalElectricityUtils {

	public static boolean isValidTarget(TileEntity target) {
		return target instanceof IVoltage ||
			   target instanceof IElectricityStorage ||
			   target instanceof IConductor;
	}

	public static Map getDetails(World world, Object obj, boolean additional) {
		HashMap response = new HashMap();
		if (additional) {
			if (obj instanceof IVoltage) {
				response.put("Voltage", ((IVoltage)obj).getVoltage());
			}
			if (obj instanceof IElectricityStorage) {
				response.put("Stored", ((IElectricityStorage)obj).getJoules());
				response.put("MaxStorage", ((IElectricityStorage)obj).getMaxJoules());
			}
			if (obj instanceof IConductor) {
				response.put("ConductorCapacity", ((IConductor)obj).getCurrentCapcity());
				response.put("Resistance", ((IConductor)obj).getResistance());
			}
		}
		return response;
	}
}
