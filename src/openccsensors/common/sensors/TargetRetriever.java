package openccsensors.common.sensors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.api.ITargetWrapper;
import openccsensors.common.helper.EntityHelper;

public class TargetRetriever {

	ArrayList<ITargetWrapper> targets = new ArrayList<ITargetWrapper>();
	
	public void registerTarget(ITargetWrapper wrapper)
	{
		targets.add(wrapper);
	}
	
	public HashMap<String, ArrayList<ISensorTarget>> getAdjacentTiles(World world, int sx, int sy, int sz)
	{
		return getAdjacentTiles(world, sx, sy, sz, 4);
	}
	
	public HashMap<String, ArrayList<ISensorTarget>> getAdjacentTiles(World world, int sx, int sy, int sz, int distance)
	{
		HashMap<String, ArrayList<ISensorTarget>> map = new HashMap<String, ArrayList<ISensorTarget>>();

		for (int x=-distance; x <= distance; x++)
		{
			for (int y =- distance; y <= distance; y++)
			{
				for (int z =- distance; z <= distance; z++)
				{
					addTileEntityToHashMapIfValid(sx, sy, sz, world.getBlockTileEntity(sx + x, sy + y, sz + z), map, String.format("%s,%s,%s", x, y, z));
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
	
	public HashMap<String, ArrayList<ISensorTarget>> getEntities(World world, int sx, int sy, int sz, double radius, int direction)
	{
		HashMap<String, ArrayList<ISensorTarget>> map = new HashMap<String, ArrayList<ISensorTarget>>();
		// very temporary and inefficient method:
		PyramidIterator it = new PyramidIterator(sx,sy,sz,(int) radius,direction);
		while (it.hasNext()) {
			Vec3 vec = (Vec3) it.next();
			for (Entry<String, Entity> entity : EntityHelper.getEntities(world, (int)vec.xCoord, (int)vec.yCoord, (int)vec.zCoord, 0).entrySet())
			{
				addTileEntityToHashMapIfValid(sx, sy, sz, entity.getValue(), map, entity.getKey());
			}
		}
				
		return map;
	}

	private void addTileEntityToHashMapIfValid(int sx, int sy, int sz, Object entity, HashMap<String, ArrayList<ISensorTarget>> map, String name)
	{
		if (entity != null)
		{
			for (ITargetWrapper wrapper : targets)
			{
				ISensorTarget target = wrapper.createNew(entity, sx, sy, sz);
				if (target != null)
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
					arr.add(target);
				}
			}
		}
	}
	
	// maths are wrong in this one
	public class PyramidIterator implements Iterator
	{
		private int currentIndex;
		private int range;
		private Vec3 origin;
		private float rotation;
		
		public PyramidIterator(int x, int y, int z, int direction, int range)
		{
			this.currentIndex = 0;
			this.range = range;
			this.origin = Vec3.createVectorHelper(x, y, z);
			this.rotation = (float) (-(direction%4)*Math.PI/2);
		}
		
		private int getDepth(int index)
		{
			double x = Math.pow(-1+81*index+9*Math.pow(-2*index+81*index*index, 2), 3);
			return (int) (Math.ceil((1.0F/6.0F)*(2.0F+1.0F/x+x)));
		}
		
		private Vec3 findRelative(int index)
		{
			int z = getDepth(index); // depth of the smallest pyramid with volume >= index
			int offset = z - (int) (((4*z*z-4*z+1)*z)/3.0F);
			int y = (int) Math.floor((float)offset/(float)(z*2-1));
			int x = offset%(z*2-1);
			return Vec3.createVectorHelper(x, y, z);
		}
		
		private Vec3 findAbsolute(int index)
		{
			Vec3 relative = findRelative(index);
			relative.rotateAroundY(this.rotation);
			return this.origin.addVector(relative.xCoord, relative.yCoord,relative.zCoord);
		}
		
		@Override
		public boolean hasNext() {
			return getDepth(currentIndex) <= range;
		}

		@Override
		public Object next() {
			return findAbsolute(currentIndex++);
		}

		@Override
		public void remove() {
		}
	}
	
}
