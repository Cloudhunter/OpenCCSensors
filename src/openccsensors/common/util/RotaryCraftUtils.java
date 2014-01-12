package openccsensors.common.util;

import java.util.HashMap;

import net.minecraft.world.World;

import Reika.RotaryCraft.API.PowerGenerator;
import Reika.RotaryCraft.API.ShaftMachine;
import Reika.RotaryCraft.API.ThermalMachine;

public class RotaryCraftUtils {

	public static boolean isValidPowerTarget(Object target) {
		return target != null && ((target instanceof ShaftMachine) || (target instanceof PowerGenerator));
	}
	
	public static boolean isValidMachineTarget(Object target) {
		return target != null && (target instanceof ThermalMachine);
	}
	
	public static HashMap getPowerDetails(World world, Object obj, boolean additional) {
		HashMap response = new HashMap();
		
		if (obj == null || (!(obj instanceof ShaftMachine) && !(obj instanceof PowerGenerator)) || !additional) {
			return response;
		}
		
		if (obj instanceof ShaftMachine) {
			ShaftMachine shaftMachine = (ShaftMachine) obj;
			response.put("Speed", shaftMachine.getOmega());
			response.put("Torque", shaftMachine.getTorque());
			response.put("Power", shaftMachine.getPower());
		}
		if (obj instanceof PowerGenerator) {
			PowerGenerator powerGen = (PowerGenerator) obj;
			response.put("PowerOutput", powerGen.getCurrentPower());
			response.put("PowerOutputMax", powerGen.getMaxPower());
		}
		
		return response;
	}
	
	public static HashMap getMachineDetails(World world, Object obj, boolean additional) {
		HashMap response = new HashMap();
		
		if (obj == null || !(obj instanceof ThermalMachine) || !additional) {
			return response;
		}
		
		ThermalMachine machine = (ThermalMachine) obj;
		int heat = machine.getTemperature();
		int maxHeat = machine.getMaxTemperature();
		response.put("Heat", heat);
		response.put("MaxHeat", maxHeat);
		response.put("HeatPercentage", 0);
		
		if (maxHeat > 0) {
			response.put("HeatPercentage", Math.max(Math.min(100,((100.0 / maxHeat) * heat)), 0));
		}
		return response;
	}
}
