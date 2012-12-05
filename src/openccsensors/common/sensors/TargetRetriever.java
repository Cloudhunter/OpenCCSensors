package openccsensors.common.sensors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.Vec3;
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
	
	public HashMap<String, ArrayList<ISensorTarget>> getLivingEntities(World world, int sx, int sy, int sz, double radius, int direction)
	{
		HashMap<String, ArrayList<ISensorTarget>> map = new HashMap<String, ArrayList<ISensorTarget>>();
		// very temporary and inefficient method:
		PyramidIterator it = new PyramidIterator(sx,sy,sz,(int) radius,direction);
		while (it.hasNext()) {
			Vec3 vec = (Vec3) it.next();
			for (Entry<String, EntityLiving> entity : LivingEntityHelper.getLivingEntities(world, (int)vec.xCoord, (int)vec.yCoord, (int)vec.zCoord, 0).entrySet())
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
