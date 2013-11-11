package openccsensors.common.util;

import java.util.HashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import mithion.arsmagica.api.power.IPowerIntegration;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;

public class MagicUtils {

	public static boolean isValidAspectTarget(Object target) {
		return target != null && target instanceof IAspectContainer;
	}

	public static boolean isValidEssenceTarget(Object target) {
		return target != null && target instanceof IPowerIntegration;
	}

	public static HashMap getMapOfAspects(World world, Object obj, boolean additional) {

		HashMap response = new HashMap();

		if (obj == null || !(obj instanceof TileEntity) || !additional) {
			return response;
		}

		if (obj instanceof IAspectContainer) {
			IAspectContainer aspectContainer = (IAspectContainer) obj
			AspectList aspectList = aspectContainer.getAspects()
			for (Map.Entry<Aspect, Integer> entry : aspectList.aspects) {
				response.put(entry.getKey().getName(), entry.getValue())
			}
		}
		return response;
	}

	public static HashMap getMapOfArsMagicaPower(World world, Object obj, boolean additional) {
		HashMap response = new HashMap();

		if (obj == null || !(obj instanceof TileEntity) || !additional) {
			return response;
		}

		if (obj instanceof IPowerIntegration) {
			powerObj = (IPowerIntegration) obj;
			response.put("EssenceCharge", powerObj.getCharge());
			response.put("EssenceCapacity", powerObj.getCapacity());
			response.put("EssenceDeficit", powerObj.getDefecit());
			response.put("EssenceDeficitThreshold", powerObj.getDefecitThreshold());
			response.put("IsNexus", powerObj.isNexus());
			response.put("IsNexusImpersonator", powerObj.isNexusImpersonator());
			response.put("HasNexusPath", powerObj.hasNexusPath());
			response.put("HopsToNexus", powerObj.getNumHopsToNexus());
			response.put("ChargeRate", powerObj.getChargeRate());
		}
		return response;
	}
}