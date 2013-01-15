package openccsensors.common.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;

public class TargetHelper {
	
	public static HashMap mergeSensorTargets(HashMap<String, ArrayList<ISensorTarget>> targetMap, World world)
	{
		HashMap<String, HashMap> results = new HashMap<String, HashMap>();
		HashMap properties;
		for (Entry<String, ArrayList<ISensorTarget>> entry : targetMap.entrySet())
		{
			properties = new HashMap();
			for (ISensorTarget target : entry.getValue())
			{
				properties.putAll(target.getBasicDetails(world));
			}
			results.put(entry.getKey(), properties);
		}
		return results;
	}
	
	public static HashMap mergeTargetDetails(ArrayList<ISensorTarget> targets, World world) {
		
		HashMap details = new HashMap();
		for (ISensorTarget target : targets)
		{
			details.putAll(target.getExtendedDetails(world));
		}
		
		return details;
	}
	
}
