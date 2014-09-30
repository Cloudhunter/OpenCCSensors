package openccsensors.common.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import universalelectricity.core.block.IConductor;
import universalelectricity.core.block.IElectrical;
import universalelectricity.core.block.IElectricalStorage;

public class UniversalElectricityUtils {

	public static boolean isValidTarget(TileEntity target) {
		return target instanceof IElectrical ||
			   target instanceof IElectricalStorage ||
			   target instanceof IConductor;
	}

	public static Map getDetails(World world, Object obj, boolean additional) {
		HashMap response = new HashMap();
		
		if (additional) {
			if (obj instanceof IElectrical) {
				response.put("Voltage", ((IElectrical)obj).getVoltage());
			}
			
			if (obj instanceof IElectricalStorage) {
				double joules = ((IElectricalStorage)obj).getEnergyStored();
				double maxJoules = ((IElectricalStorage)obj).getMaxEnergyStored();
				response.put("Stored", joules);
				response.put("MaxStorage", maxJoules);
				if (maxJoules > 0) {
					double percent = (double)100 / maxJoules * joules;
					percent = Math.max(Math.min(percent, 100), 0);
					response.put("PowerPercentFull", percent);
				}
			}
			
			if (obj instanceof IConductor) {
				response.put("ConductorCapacity", ((IConductor)obj).getCurrentCapacity());
				response.put("Resistance", ((IConductor)obj).getResistance());
			}
			
		}
		return response;
	}
}
