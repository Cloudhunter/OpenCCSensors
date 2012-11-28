package openccsensors.common.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.src.World;

import openccsensors.common.core.ISensorTarget;

public class TargetHelper {
	
	public static Map getBasicInformationForTargets(HashMap<String, ISensorTarget> targets, World world)
	{

		HashMap<String, Object> retMap = new HashMap<String, Object>();
		
		for (Entry<String, ISensorTarget> entry : targets.entrySet())
		{
			ISensorTarget target = entry.getValue();
			if (target != null)
			{
				retMap.put(entry.getKey(), target.getBasicInformation(world));
			}
		}
		
		return retMap;
	}
	public static Map getDetailedInformationForTarget(String target, HashMap<String, ISensorTarget> targets, World world) {

		if (targets.containsKey(target))
		{
			ISensorTarget sensorTarget = targets.get(target);
			if (sensorTarget != null)
			{
				return sensorTarget.getDetailInformation(world);
			}
		}
		
		return null;
	}
}
