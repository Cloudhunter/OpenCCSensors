package openccsensors.common.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.core.OCSLog;

public class TargetHelper {
	
	public static Map getBasicInformationForTargets(HashMap<String, ArrayList<ISensorTarget>> directions, World world)
	{
		HashMap<String, Object> retMap = new HashMap<String, Object>();
		
		for (Entry<String, ArrayList<ISensorTarget>> entry : directions.entrySet())
		{
			ArrayList<ISensorTarget> targets = entry.getValue();

			for (ISensorTarget target : targets)
			{
				if (target != null)
				{
					Map map = (Map) retMap.get(entry.getKey());
					if (map == null)
					{
						map = new HashMap();
						retMap.put(entry.getKey(), map);
					}
					map.putAll(target.getBasicInformation(world));
				}
			}
		}
		
		return retMap;
	}
	public static Map getDetailedInformationForTarget(String targetId, HashMap<String, ArrayList<ISensorTarget>> targets, World world) {

		if (targets.containsKey(targetId))
		{
			ArrayList<ISensorTarget> sensorTargets = targets.get(targetId);
			HashMap rtn = new HashMap();
			if (sensorTargets != null)
			{
				for (ISensorTarget target : sensorTargets)
				{
					rtn.putAll(target.getBasicInformation(world));
					rtn.putAll(target.getDetailInformation(world));
				}
			}
			return rtn;
		}
		
		return null;
	}
}
