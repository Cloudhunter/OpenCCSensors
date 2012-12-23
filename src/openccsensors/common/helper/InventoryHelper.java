package openccsensors.common.helper;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
			map.put("Name", "empty");
			map.put("Size", 0);
			map.put("Damagevalue", 0);
			return map; // empty item
		}
		
		Item item = itemstack.getItem();
		
		map.put("Name", item.getItemNameIS(itemstack));
		map.put("Size", itemstack.stackSize);
		map.put("DamageValue", itemstack.getItemDamage());
		
		if (itemstack.hasTagCompound())
		{
			map.put("nbt", NetworkHelper.NBTToMap(itemstack.getTagCompound()));
		}
		
		return map;
	}
	
}
