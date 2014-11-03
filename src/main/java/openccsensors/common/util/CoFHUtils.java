package openccsensors.common.util;

import java.util.HashMap;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

public class CoFHUtils {

	public static boolean isValidPowerTarget(Object target) {
		return target != null && (target instanceof IEnergyHandler);
	}
	
	public static boolean isValidMachineTarget(Object target) {
		return target != null && (target instanceof IEnergyHandler);
	}
	
	public static HashMap getPowerDetails(World world, Object obj, boolean additional) {
		HashMap response = new HashMap();

		if (obj == null || !(obj instanceof IEnergyHandler) || !additional) {
			return response;
		}

		if (obj instanceof IEnergyHandler) {
			IEnergyHandler energyHandler = (IEnergyHandler) obj;
			int stored = energyHandler.getEnergyStored(ForgeDirection.UNKNOWN);
			int capacity = energyHandler.getMaxEnergyStored(ForgeDirection.UNKNOWN);
			response.put("Stored", stored);
			response.put("Capacity", capacity);
			response.put("StoredPercentage", 0);

			if (capacity > 0) {
				response.put("StoredPercentage", Math.max(Math.min(100,((100.0 * stored) / capacity)), 0));
			}
		}
		return response;
	}

	public static HashMap getMachineDetails(World world, Object obj, boolean additional) {
		HashMap response = new HashMap();

		if (obj == null || !(obj instanceof IEnergyHandler) || !additional) {
			return response;
		}

		if (obj instanceof IEnergyHandler) {
			IEnergyHandler energyHandler = (IEnergyHandler) obj;
			int stored = energyHandler.getEnergyStored(ForgeDirection.UNKNOWN);
			int capacity = energyHandler.getMaxEnergyStored(ForgeDirection.UNKNOWN);
			response.put("Stored", stored);
			response.put("Capacity", capacity);
			response.put("StoredPercentage", 0);

			if (capacity > 0) {
				response.put("StoredPercentage", Math.max(Math.min(100,((100.0 * stored) / capacity)), 0));
			}
		}
		return response;
	}
}
