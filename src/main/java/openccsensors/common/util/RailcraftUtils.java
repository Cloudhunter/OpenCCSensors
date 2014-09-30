package openccsensors.common.util;

import java.util.HashMap;

import mods.railcraft.api.carts.IEnergyTransfer;
import mods.railcraft.api.carts.IExplosiveCart;
import net.minecraft.entity.item.EntityMinecart;


public class RailcraftUtils {
	
	public static HashMap getEnergyDetails(EntityMinecart minecart) {
		HashMap response = new HashMap();
		if (minecart instanceof IEnergyTransfer) {
			IEnergyTransfer energyCart = (IEnergyTransfer) minecart;
			int capacity = energyCart.getCapacity();
			int stored = energyCart.getEnergy();
			
			response.put("Stored", stored);
			response.put("Capacity", capacity);
			response.put("Output", energyCart.getTransferLimit());
			response.put("StoredPercentage", 0);
			
			if (capacity > 0) {
				response.put("StoredPercentage", Math.max(Math.min(100,((100.0 / capacity) * stored)), 0));
			}
		}
		return response;
	}
	
	public static HashMap getExplosiveDetails(EntityMinecart minecart) {
		HashMap response = new HashMap();
		if (minecart instanceof IExplosiveCart) {
			IExplosiveCart explosiveCart = (IExplosiveCart) minecart;
			response.put("IsPrimed", explosiveCart.isPrimed());
			if (explosiveCart.isPrimed()) {
				response.put("FuseLength", explosiveCart.getFuse());
				response.put("BlastRadius", explosiveCart.getBlastRadius());
			}
		}
		return response;
	}

}
