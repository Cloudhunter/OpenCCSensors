package openccsensors.common.sensors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import openccsensors.common.core.ISensorTarget;

import cpw.mods.fml.common.FMLCommonHandler;

import net.minecraft.src.World;

public class GenericSensorInterface {

	
	public HashMap<String, ISensorTarget> getAvailableTargets(World world, int x, int y, int z)
	{
		return new HashMap<String, ISensorTarget>();
	}
	
	public Map getBasicTarget(World world, int x, int y, int z)
	{
		
	    HashMap retMap = new HashMap();
	    
	    HashMap<String, ISensorTarget> targets = getAvailableTargets(world, x, y, z);
	    
	    Iterator it = targets.entrySet().iterator();

	    while (it.hasNext())
	    {
	    	Map.Entry<String, ISensorTarget> pairs =  (Entry<String, ISensorTarget>) it.next();
	    	
	    	String key = pairs.getKey();
	    	ISensorTarget target = pairs.getValue();
	    	
	    	retMap.put(key, target == null ? null : target.getBasicInformation(world));
	    }
	    
	    
		return retMap;
	}

	public Map getTargetDetails(World world, int x, int y, int z, String target)
	{
		Map targets = getAvailableTargets(world, x, y, z);
		
		ISensorTarget sensorTarget = (ISensorTarget) targets.get(target);
		if (target == null)
		{
			return null;
		}
		
		return sensorTarget.getDetailInformation(world);
	}
	
}
