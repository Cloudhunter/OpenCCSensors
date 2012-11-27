package openccsensors.common.helper;

import java.util.HashMap;

import openccsensors.common.core.OCSLog;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockTileHelper {

	public static HashMap<String, TileEntity> getAdjacentTile(World world, int x, int y, int z, Class[] clazzes)
	{
		HashMap map = new HashMap<String, TileEntity>();
		
		addToHashMap(world.getBlockTileEntity(x + 1, y, z), map, clazzes, "EAST");
		addToHashMap(world.getBlockTileEntity(x - 1, y, z), map, clazzes, "WEST");
		addToHashMap(world.getBlockTileEntity(x, y + 1, z), map, clazzes, "UP");
		addToHashMap(world.getBlockTileEntity(x, y - 1, z), map, clazzes, "DOWN");
		addToHashMap(world.getBlockTileEntity(x, y, z + 1), map, clazzes, "SOUTH");
		addToHashMap(world.getBlockTileEntity(x, y, z - 1), map, clazzes, "NORTH");
		
		return map;
		
	}

	
	public static boolean addToHashMap( TileEntity tile, HashMap<String, TileEntity> map, Class[] clazzes, String name )
	{

		if (tile != null)
		{
			for (Class clazz : clazzes)
			{
				if (clazz.isInstance(tile))
				{
					String _name = name;
					if ( _name == null )
					{
						_name = tile.toString();
					}
					
					map.put( _name, tile );
					return true;					
				}
			}
		}
		return false;
	}
	
}
