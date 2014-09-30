package openccsensors.common.util;

import java.util.HashMap;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.tileentity.IEnergyInfo;


public class ThermalExpansionUtils {

	public static boolean isValidPowerTarget(Object target) {
		return target != null && (target instanceof IEnergyHandler);
	}
	
	public static boolean isValidMachineTarget(Object target) {
		return target != null && (target instanceof IEnergyInfo);
	}
	
	public static HashMap getPowerDetails(World world, Object obj, boolean additional) {
		HashMap response = new HashMap();

		if (obj == null || !(obj instanceof IEnergyHandler) || !additional) {
			return response;
		}

		if (obj instanceof IEnergyHandler) {
			System.out.println("Is an IEnergyHandler");
			IEnergyHandler energyHandler = (IEnergyHandler) obj;
			System.out.println("Cast Successful");
			int stored = energyHandler.getEnergyStored(ForgeDirection.UNKNOWN);
			System.out.println(String.format("stored: %d", stored));
			int capacity = energyHandler.getMaxEnergyStored(ForgeDirection.UNKNOWN);
			System.out.println(String.format("capacity: %d", capacity));
			response.put("Stored", stored);
			response.put("Capacity", capacity);
			response.put("StoredPercentage", 0);

			if (capacity > 0) {
				response.put("StoredPercentage", Math.max(Math.min(100,((100.0 / capacity) * stored)), 0));
			}
		}
		return response;
	}

	public static HashMap getMachineDetails(World world, Object obj, boolean additional) {
		HashMap response = new HashMap();
		
		if (obj == null || !(obj instanceof IEnergyInfo) || !additional) {
			return response;
		}
		
		if (obj instanceof IEnergyInfo) {
			IEnergyInfo energyInfo = (IEnergyInfo) obj;
			int energyTick = energyInfo.getEnergyPerTick();
			int energyTickMax = energyInfo.getMaxEnergyPerTick();
			int stored = energyInfo.getEnergy();
			int capacity = energyInfo.getMaxEnergy();
			response.put("Stored", stored);
			response.put("Capacity", capacity);
			response.put("EnergyUsage", energyTick);
			response.put("EnergyUsageMax", energyTickMax);
			response.put("StoredPercentage", 0);
			response.put("UsagePercentage", 0);
			
			if (capacity > 0) {
				response.put("StoredPercentage", Math.max(Math.min(100,((100.0 / capacity) * stored)), 0));
			}
			if (energyTickMax > 0) {
				response.put("UsagePercentage", Math.max(Math.min(100,((100.0 / energyTickMax) * energyTick)), 0));
			}
		}
		
		return response;
	}
}
