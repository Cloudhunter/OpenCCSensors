package openccsensors.common.helper;

import java.util.HashMap;

import openccsensors.common.api.ISensorTarget;
import openccsensors.common.core.OCSLog;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockTileHelper {

	public static boolean addEntityToHashMapIfValid( TileEntity tile, HashMap<String, TileEntity> map, Class[] clazzes, String name )
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
