package openccsensors.common.util;

import java.util.HashMap;

import net.minecraft.world.World;
import buildcraft.api.power.IPowerReceptor;

public class BuildcraftUtils {
	public static boolean isValidPowerTarget(Object target) {
		return target != null && (target instanceof IPowerReceptor);
	}
	
	public static HashMap getPowerDetails(World world, Object obj, boolean additional) {
		HashMap response = new HashMap();
		return response;
	}
}
