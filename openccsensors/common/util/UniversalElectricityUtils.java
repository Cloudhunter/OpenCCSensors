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
				double joules = ((IElectricityStorage)obj).getJoules();
				double maxJoules = ((IElectricityStorage)obj).getMaxJoules();
				response.put("Stored", joules);
				response.put("MaxStorage", maxJoules);
				if (maxJoules > 0) {
					double percent = 100 / maxJoules * joules;
					percent = Math.max(Math.min(percent, 100), 0);
					response.put("PercentFull", (int)percent);
				}
			}
			
			if (obj instanceof IConductor) {
				response.put("ConductorCapacity", ((IConductor)obj).getCurrentCapcity());
				response.put("Resistance", ((IConductor)obj).getResistance());
			}
			
		}
		return response;
	}
}
