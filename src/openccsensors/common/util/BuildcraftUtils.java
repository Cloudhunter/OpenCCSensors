package openccsensors.common.util;

import java.util.HashMap;

import net.minecraft.world.World;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;

public class BuildcraftUtils {
	
	public static boolean isValidPowerTarget(Object target) {
		return target != null && (target instanceof IPowerReceptor);
	}
	
	public static HashMap getPowerDetails(World world, Object obj, boolean additional) {
		HashMap response = new HashMap();
		if (obj instanceof IPowerReceptor) {
			IPowerReceptor receptor = (IPowerReceptor) obj;
			IPowerProvider provider = receptor.getPowerProvider();
			if (provider != null) {
				response.put("ActivationEnergy", provider.getActivationEnergy());
				response.put("EnergyStored", provider.getEnergyStored());
				response.put("MaxEnergyReceived", provider.getMaxEnergyReceived());
				response.put("MaxEnergyStored", provider.getMaxEnergyStored());
				response.put("MinEnergyReceived", provider.getMinEnergyReceived());
			}
		}
		return response;
	}
}
