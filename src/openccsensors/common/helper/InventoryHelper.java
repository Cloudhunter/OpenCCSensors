package openccsensors.common.helper;

import java.util.HashMap;
import java.util.Map;


import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class InventoryHelper {

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
			map.put("nbt", NetworkHelper.NBTToMap(itemstack.getTagCompound()));
		}
		
		return map;
	}
	
}
