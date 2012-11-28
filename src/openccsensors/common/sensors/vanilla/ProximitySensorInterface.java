package openccsensors.common.sensors.vanilla;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;
import openccsensors.common.core.ISensorInterface;
import openccsensors.common.core.ISensorTarget;
import openccsensors.common.helper.LivingEntityHelper;
import openccsensors.common.sensors.GenericSensorInterface;

public class ProximitySensorInterface extends GenericSensorInterface implements ISensorInterface 
{

	private final double sensingRadius = 16.0F;

	@Override
	public String getName() 
	{
		return "proximity";
	}
	
	@Override
	public HashMap<String, ISensorTarget> getAvailableTargets(World world, int x, int y, int z)
	{

		HashMap<String, ISensorTarget> targets = new HashMap<String, ISensorTarget>();

		HashMap<String, EntityLiving> entities = LivingEntityHelper.getLivingEntities(world, x, y, z, sensingRadius);

		Iterator it = entities.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, EntityLiving> pairs = (Entry<String, EntityLiving>) it.next();
			targets.put(pairs.getKey(), new LivingTarget(pairs.getValue(), x, y, z));
		}

		return targets;
	}

	@Override
	public String[] getMethods() 
	{
		return null;
	}

	@Override
	public Object[] callMethod(int methodID, Object[] args) throws Exception 
	{
		return null;
	}

}
