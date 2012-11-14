package openccsensors.common.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagByte;
import net.minecraft.src.NBTTagByteArray;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagDouble;
import net.minecraft.src.NBTTagFloat;
import net.minecraft.src.NBTTagInt;
import net.minecraft.src.NBTTagIntArray;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NBTTagLong;
import net.minecraft.src.NBTTagShort;
import net.minecraft.src.NBTTagString;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class SensorHelper
{
	public static HashMap<String, TileEntity> getAdjacentTile(World world, int x, int y, int z, Class clazz)
	{
		HashMap map = new HashMap<String, TileEntity>();
		addToHashMap(world.getBlockTileEntity(x + 1, y, z), map, clazz, "EAST");
		addToHashMap(world.getBlockTileEntity(x - 1, y, z), map, clazz, "WEST");
		addToHashMap(world.getBlockTileEntity(x, y + 1, z), map, clazz, "UP");
		addToHashMap(world.getBlockTileEntity(x, y - 1, z), map, clazz, "DOWN");
		addToHashMap(world.getBlockTileEntity(x, y, z + 1), map, clazz, "SOUTH");
		addToHashMap(world.getBlockTileEntity(x, y, z - 1), map, clazz, "NORTH");
		
		return map;
		
	}
	
	public static boolean addToHashMap( TileEntity tile, HashMap<String, TileEntity> map, Class clazz, String name )
	{
		if (tile != null && (clazz == null || clazz.isInstance(tile)))
		{
			String _name = name;
			if ( _name == null )
			{
				_name = tile.toString();
			}
			
			map.put( _name, tile );

			return true;
		}
		
		return false;
	}
	
	public static String getType ( String rawType )
	{
		String translated = LanguageRegistry.instance().getStringLocalization( rawType, "en_OCS" );
		return translated == "" ? rawType : translated;
	}
	
	public static Map invToMap( IInventory inventory )
	{
		HashMap map = new HashMap();
		for ( int i = 0; i < inventory.getSizeInventory(); i++ )
		{
			map.put( i + 1, itemstackToMap(inventory.getStackInSlot(i)) );
		}
		
		return map;
	}
	
	public static Map itemstackToMap( ItemStack itemstack )
	{
		HashMap map = new HashMap();
		
		if (itemstack == null)
		{
			map.put("name", "empty");
			map.put("size", 0);
			map.put("damagevalue", 0);
			return map; // empty item
		}
		
		Item item = itemstack.getItem();
		
		map.put("name", item.getItemNameIS(itemstack));
		map.put("size", itemstack.stackSize);
		map.put("damageValue", itemstack.getItemDamage());
		
		if (itemstack.hasTagCompound())
		{
			map.put("nbt", NBTToMap(itemstack.getTagCompound()));
		}
		
		return map;
	}

	private static Map NBTToMap( NBTTagCompound tagCompound )
	{
		HashMap map = new HashMap();
		
		Collection col = tagCompound.getTags();
		
		Iterator it = col.iterator();
		
        while (it.hasNext())
        {
            NBTBase nbt = (NBTBase)it.next();
            map.put(nbt.getName(), NBTToObj(nbt));
            
        }
		
		return map;
	}
	
	private static Object NBTToObj( NBTBase nbt )
	{
		switch (nbt.getId())
		{
			case 1:
				return ((NBTTagByte) nbt).data;
			case 2:
				return ((NBTTagShort) nbt).data;
			case 3:
				return ((NBTTagInt) nbt).data;
			case 4:
				return ((NBTTagLong) nbt).data;
			case 5:
				return ((NBTTagFloat) nbt).data;
			case 6:
				return ((NBTTagDouble) nbt).data;
			case 7:
				HashMap map = new HashMap();
				byte[] byteArray = ((NBTTagByteArray) nbt).byteArray; 
				for (int i = 0; i < byteArray.length; i++)
				{
					map.put(i + 1, byteArray[i]);
				}
				return map;
			case 8:
				return ((NBTTagString) nbt).data;
			case 9:
				HashMap map1 = new HashMap();
				NBTTagList list = (NBTTagList) nbt;
				for (int i = 0; i < list.tagCount(); i++)
				{
					map1.put(i + 1, NBTToObj(list.tagAt(i)));
				}
				return map1;
			case 10:
				return NBTToMap((NBTTagCompound) nbt);
			case 11:
				HashMap map3 = new HashMap();
				int[] intArray = ((NBTTagIntArray) nbt).intArray; 
				for (int i = 0; i < intArray.length; i++)
				{
					map3.put(i + 1, intArray[i]);
				}
				return map3;
			default:
				return "";
		}
		
	}
	
	
}
