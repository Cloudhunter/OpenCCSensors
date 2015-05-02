package openccsensors.common.util;

import java.util.HashMap;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;

public class CoFHUtils {

	public static boolean isValidPowerTarget(Object target) {
		return target != null && (
				target instanceof IEnergyHandler ||
				target instanceof IEnergyProvider
				);
	}
	
	public static boolean isValidMachineTarget(Object target) {
		return target != null && (
				target instanceof IEnergyHandler ||
				target instanceof IEnergyProvider
				);
	}
	
	public static HashMap getPowerDetails(World world, Object obj, boolean additional) {
		HashMap response = new HashMap();

		if (obj == null || !(obj instanceof IEnergyHandler || obj instanceof IEnergyProvider) || !additional) {
			return response;
		}

		int stored;
		int capacity;
		
		if (obj instanceof IEnergyHandler || obj instanceof IEnergyProvider) {
			if (obj instanceof IEnergyHandler) {
				IEnergyHandler energyHandler = (IEnergyHandler) obj;
				stored = energyHandler.getEnergyStored(ForgeDirection.UNKNOWN);
				capacity = energyHandler.getMaxEnergyStored(ForgeDirection.UNKNOWN);
			} else if (obj instanceof IEnergyProvider) {
				IEnergyProvider energyProvider = (IEnergyProvider) obj;
				stored = energyProvider.getEnergyStored(ForgeDirection.UNKNOWN);
				capacity = energyProvider.getMaxEnergyStored(ForgeDirection.UNKNOWN);
			} else {
				return response;
			}
			
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

		if (obj == null || !(obj instanceof IEnergyHandler || obj instanceof IEnergyProvider) || !additional) {
			return response;
		}

		int stored;
		int capacity;
		
		if (obj instanceof IEnergyHandler || obj instanceof IEnergyProvider) {
			if (obj instanceof IEnergyHandler) {
				IEnergyHandler energyHandler = (IEnergyHandler) obj;
				stored = energyHandler.getEnergyStored(ForgeDirection.UNKNOWN);
				capacity = energyHandler.getMaxEnergyStored(ForgeDirection.UNKNOWN);
			} else if (obj instanceof IEnergyProvider) {
				IEnergyProvider energyProvider = (IEnergyProvider) obj;
				stored = energyProvider.getEnergyStored(ForgeDirection.UNKNOWN);
				capacity = energyProvider.getMaxEnergyStored(ForgeDirection.UNKNOWN);
			} else {
				return response;
			}
			
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
