package openccsensors.common.util;

import java.util.HashMap;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler.PowerReceiver;

public class BuildcraftUtils {
	
	public static boolean isValidPowerTarget(Object target) {
		return target != null && (target instanceof IPowerReceptor);
	}
	
	public static HashMap getPowerDetails(World world, Object obj, boolean additional) {
		HashMap response = new HashMap();
		if (obj instanceof IPowerReceptor) {
			IPowerReceptor receptor = (IPowerReceptor) obj;
			PowerReceiver receiver = receptor.getPowerReceiver(ForgeDirection.UNKNOWN);
			if (receiver != null) {
				response.put("ActivationEnergy", receiver.getActivationEnergy());
				response.put("EnergyStored", receiver.getEnergyStored());
				response.put("MaxEnergyReceived", receiver.getMaxEnergyReceived());
				response.put("MaxEnergyStored", receiver.getMaxEnergyStored());
				response.put("MinEnergyReceived", receiver.getMinEnergyReceived());
			}
		}
		return response;
	}
}
