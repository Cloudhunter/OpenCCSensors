package openccsensors.common.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import resonantengine.api.graph.node.IEnergyNode;

public class UniversalElectricityUtils {

	public static boolean isValidTarget(TileEntity target) {
		return target instanceof IEnergyNode;
	}

	public static Map getDetails(World world, Object obj, boolean additional) {
		HashMap response = new HashMap();
		
		if (additional) {
			if (obj instanceof IEnergyNode) {
				double joules = ((IEnergyNode)obj).getEnergy(ForgeDirection.UNKNOWN);
				double maxJoules = ((IEnergyNode)obj).getEnergyCapacity(ForgeDirection.UNKNOWN);
				response.put("Stored", joules);
				response.put("MaxStorage", maxJoules);
				if (maxJoules > 0) {
					double percent = (double)100 / maxJoules * joules;
					percent = Math.max(Math.min(percent, 100), 0);
					response.put("PowerPercentFull", percent);
				}
			}
		}
		return response;
	}
}
