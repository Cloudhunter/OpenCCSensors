package openccsensors.common.sensors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;

import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITargetWrapper;
import openccsensors.common.helper.LivingEntityHelper;

public class TargetRetriever {

	ArrayList<TargetPair> targets = new ArrayList<TargetPair>();
	
	public void registerTarget(Class entity, ITargetWrapper wrapper)
	{
		targets.add(new TargetPair(entity, wrapper));
	}
	
	public void registerTargets(Class[] entities, ITargetWrapper wrapper)
	{
		for (Class entityClass : entities)
		{
			targets.add(new TargetPair(entityClass, wrapper));
		}
	}
	
	public HashMap<String, ArrayList<ISensorTarget>> getAdjacentTiles(World world, int sx, int sy, int sz)
	{
		HashMap<String, ArrayList<ISensorTarget>> map = new HashMap<String, ArrayList<ISensorTarget>>();

		for (int x=-2; x <= 2; x++)
		{
			for (int y =- 2; y <= 2; y++)
			{
				for (int z =- 2; z <= 2; z++)
				{
					addTileEntityToHashMapIfValid(sx, sy, sz, world.getBlockTileEntity(sx + x, sy + y, sz + z), map, String.format("%s,%s,%s", x, y, z));
				}
			}
		}
		
		return map;
	}
	
	public HashMap<String, ArrayList<ISensorTarget>> getLivingEntities(World world, int sx, int sy, int sz, double radius)
	{
		HashMap<String, ArrayList<ISensorTarget>> map = new HashMap<String, ArrayList<ISensorTarget>>();

		for (Entry<String, EntityLiving> entity : LivingEntityHelper.getLivingEntities(world, sx, sy, sz, radius).entrySet())
		{
			addTileEntityToHashMapIfValid(sx, sy, sz, entity.getValue(), map, entity.getKey());
		}
		
		return map;
	}
	

	private void addTileEntityToHashMapIfValid(int sx, int sy, int sz, Object entity, HashMap<String, ArrayList<ISensorTarget>> map, String name)
	{
		if (entity != null)
		{
			for (TargetPair pair : targets)
			{
				if (pair.entityClass.isInstance(entity))
				{
					String _name = name;
					if ( _name == null )
					{
						_name = entity.toString();
					}
					
					ArrayList<ISensorTarget> arr = map.get(_name);
					if (arr == null)
					{
						arr = new ArrayList<ISensorTarget>();
						map.put(_name, arr);
					}
					arr.add(pair.wrapper.createNew(entity, sx, sy, sz));
				}
			}
		}
	}
	
	
	protected class TargetPair {
		
		public Class entityClass;
		public ITargetWrapper wrapper;
		
		public TargetPair(Class entityClass, ITargetWrapper wrapper)
		{
			this.entityClass = entityClass;
			this.wrapper = wrapper;
		}
		
	}
	
}
