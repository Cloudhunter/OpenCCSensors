package openccsensors.common.sensors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITargetWrapper;
import openccsensors.common.helper.EntityHelper;

public class TargetRetriever {

	public enum Type {
	    BLOCK, TILEENTITY
	};
	
	ArrayList<ITargetWrapper> targets = new ArrayList<ITargetWrapper>();
	
	public void registerTarget(ITargetWrapper wrapper)
	{
		targets.add(wrapper);
	}

	public HashMap<String, ArrayList<ISensorTarget>> getSurroundingTileEntities(
												World world, 
												int sx,
												int sy,
												int sz
												)
	{
		return getSurroundingObjects(world, sx, sy, sz, Type.TILEENTITY);
	}
	
	public HashMap<String, ArrayList<ISensorTarget>> getSurroundingObjects(
												World world, 
												int sx,
												int sy,
												int sz,
												Type type
												)
	{
		return getSurroundingObjects(world, sx, sy, sz, type, 4);
	}
	
	public HashMap<String, ArrayList<ISensorTarget>> getSurroundingObjects(World world, int sx, int sy, int sz, Type type, int distance)
	{
		HashMap<String, ArrayList<ISensorTarget>> map = new HashMap<String, ArrayList<ISensorTarget>>();

		for (int x=-distance; x <= distance; x++)
		{
			for (int y =- distance; y <= distance; y++)
			{
				for (int z =- distance; z <= distance; z++)
				{
					Object obj = null;
					switch(type){
					case BLOCK:
						if (world.blockExists(sx + x, sy + y, sz + z))
						{
							int blockId = world.getBlockId(sx + x, sy + y, sz + z);
							int metadata = world.getBlockMetadata(sx + x, sy + y, sz + z);
							obj = new int[] { blockId, metadata };
						}
						break;
					case TILEENTITY:
						obj = world.getBlockTileEntity(sx + x, sy + y, sz + z);
						break;
					}
					if (obj != null)
					{
						addTileEntityToHashMapIfValid(sx, sy, sz, obj, map, String.format("%s,%s,%s", x, y, z));
					}
				}
			}
		}
		
		return map;
	}
	
	public HashMap<String, ArrayList<ISensorTarget>> getEntities(World world, int sx, int sy, int sz, double radius)
	{
		HashMap<String, ArrayList<ISensorTarget>> map = new HashMap<String, ArrayList<ISensorTarget>>();

		for (Entry<String, Entity> entity : EntityHelper.getEntities(world, sx, sy, sz, radius).entrySet())
		{
			addTileEntityToHashMapIfValid(sx, sy, sz, entity.getValue(), map, entity.getKey());
		}
		
		return map;
	}
	
	private void addTileEntityToHashMapIfValid(int sx, int sy, int sz, Object object, HashMap<String, ArrayList<ISensorTarget>> map, String name)
	{
		if (object != null)
		{
			for (ITargetWrapper wrapper : targets)
			{
				ISensorTarget target = wrapper.createNew(object, sx, sy, sz);
				if (target != null)
				{
					String _name = name;
					if ( _name == null )
					{
						_name = object.toString();
					}
					ArrayList<ISensorTarget> arr = map.get(_name);
					if (arr == null)
					{
						arr = new ArrayList<ISensorTarget>();
						map.put(_name, arr);
					}
					arr.add(target);
				}
			}
		}
	}
	
}
