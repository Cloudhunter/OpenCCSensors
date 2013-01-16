package openccsensors.common.sensors;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.world.World;
import openccsensors.common.api.ISensor;
import openccsensors.common.api.ISensorAccess;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.SensorUpgrade;
import openccsensors.common.sensors.targets.WorldTarget;

public class WorldSensor implements ISensor {

	@Override
	public String[] getCustomMethods(SensorUpgrade upgrade) {
		return null;
	}

	@Override
	public Object callCustomMethod(ISensorAccess sensor, World world, int x, int y, int z, int methodID, Object[] args, SensorUpgrade upgrade) {
		return null;
	}

	@Override
	public HashMap<String, ArrayList<ISensorTarget>> getSurroundingTargets(
			World world, int sx, int sy, int sz, SensorUpgrade upgrade) {
		
		HashMap<String, ArrayList<ISensorTarget>> retMap = new HashMap<String, ArrayList<ISensorTarget>>();
		ArrayList<ISensorTarget> targets = new ArrayList<ISensorTarget>();
		targets.add(new WorldTarget(sx, sy, sz));
		retMap.put("CURRENT", targets);
		return retMap;
	}

}
