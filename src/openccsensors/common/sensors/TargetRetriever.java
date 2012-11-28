package openccsensors.common.sensors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

import openccsensors.common.core.ISensorTarget;
import openccsensors.common.core.ITargetWrapper;
import openccsensors.common.helper.LivingEntityHelper;

public class TargetRetriever {

	ArrayList<TargetPair> targets = new ArrayList<TargetPair>();
	
	public void registerTarget(Class entity, ITargetWrapper wrapper)
	{
		targets.add(new TargetPair(entity, wrapper));
	}
	
	public HashMap<String, ISensorTarget> getAdjacentTiles(World world, int x, int y, int z)
	{
		return getAdjacentTiles(world, x, y, z, false);
	}
	
	public HashMap<String, ISensorTarget> getAdjacentTiles(World world, int sx, int sy, int sz, boolean includeSelf)
	{
		HashMap<String, ISensorTarget> map = new HashMap<String, ISensorTarget>();

		addTileEntityToHashMapIfValid(sx, sy, sz, world.getBlockTileEntity(sx + 1, sy, sz), map, "EAST");
		addTileEntityToHashMapIfValid(sx, sy, sz, world.getBlockTileEntity(sx - 1, sy, sz), map, "WEST");
		addTileEntityToHashMapIfValid(sx, sy, sz, world.getBlockTileEntity(sx, sy + 1, sz), map, "UP");
		addTileEntityToHashMapIfValid(sx, sy, sz, world.getBlockTileEntity(sx, sy - 1, sz), map, "DOWN");
		addTileEntityToHashMapIfValid(sx, sy, sz, world.getBlockTileEntity(sx, sy, sz + 1), map, "SOUTH");
		addTileEntityToHashMapIfValid(sx, sy, sz, world.getBlockTileEntity(sx, sy, sz - 1), map, "NORTH");
		
		if (includeSelf)
		{
			addTileEntityToHashMapIfValid(sx, sy, sz, world.getBlockTileEntity(sx, sy, sz), map, "SELF");
		}
		
		return map;
	}
	
	public HashMap<String, ISensorTarget> getLivingEntities(World world, int sx, int sy, int sz, double radius)
	{
		HashMap<String, ISensorTarget> map = new HashMap<String, ISensorTarget>();

		for (Entry<String, EntityLiving> entity : LivingEntityHelper.getLivingEntities(world, sx, sy, sz, radius).entrySet())
		{
			addTileEntityToHashMapIfValid(sx, sy, sz, entity.getValue(), map, entity.getKey());
		}
		
		return map;
	}
	

	private void addTileEntityToHashMapIfValid(int sx, int sy, int sz, Object entity, HashMap<String, ISensorTarget> map, String name)
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
					
					map.put( _name, pair.wrapper.createNew(entity, sx, sy, sz) );
					return;					
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
