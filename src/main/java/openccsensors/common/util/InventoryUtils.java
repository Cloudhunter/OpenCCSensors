package openccsensors.common.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraft.item.ItemEnchantedBook;

public class InventoryUtils {

	public static final String FACTORIZATION_BARREL_CLASS = "factorization.common.TileEntityBarrel";
	public static int[] mapColors = new int[] {
			32768, 	// black
			32, 	// lime
			16, 	// yellow
			256, 	// light gray
			16384, 	// red
			2048, 	// blue
			128, 	// gray
			8192, 	// green
			1, 		// white
			512, 	// cyan
			4096, 	// brown
			128, 	// gray
			2048, 	// blue
			4096 	// brown	
	};
	
	public static HashMap itemstackToMap(ItemStack itemstack) {
		
		if (itemstack == null) {

			return null;

		} else {
			HashMap map = new HashMap();
			map.put("Name", getNameForItemStack(itemstack));			
			map.put("RawName", getRawNameForStack(itemstack));
			map.put("Size", itemstack.stackSize);
			map.put("DamageValue", itemstack.getItemDamage());
			map.put("MaxStack", itemstack.getMaxStackSize());
			Item item = itemstack.getItem();
			if (item != null) {
				if (item instanceof ItemEnchantedBook) {
					map.put("Enchantments", getBookEnchantments(itemstack));
				}
			}

			return map;
		}

	}
	
	protected static HashMap getBookEnchantments(ItemStack stack) {
		
		HashMap response = new HashMap();
		
		ItemEnchantedBook book = (ItemEnchantedBook) stack.getItem();
        NBTTagList nbttaglist = book.func_92110_g(stack);
        int offset = 1;
        if (nbttaglist != null)
        {
            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                short short1 = ((NBTTagCompound)nbttaglist.tagAt(i)).getShort("id");
                short short2 = ((NBTTagCompound)nbttaglist.tagAt(i)).getShort("lvl");

                if (Enchantment.enchantmentsList[short1] != null)
                {
                    response.put(offset, Enchantment.enchantmentsList[short1].getTranslatedName(short2));
                    offset++;
                }
            }
        }
        return response;
	}
	
	public static HashMap invToMap(IInventory inventory) {
		HashMap map = new HashMap();
		if (inventory.getClass().getName() == FACTORIZATION_BARREL_CLASS) {
			Map details = itemstackToMap(inventory.getStackInSlot(0));
			try {
				TileEntity barrel = (TileEntity) inventory;
				NBTTagCompound compound = new NBTTagCompound();
				barrel.writeToNBT(compound);
				details.put("Size", compound.getInteger("item_count"));
				details.put("MaxStack",
						compound.getInteger("upgrade") == 1 ? 65536 : 4096);

			} catch (Exception e) {
			}
			map.put(1, details);
		} else {
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				map.put(i + 1, itemstackToMap(inventory.getStackInSlot(i)));
			}
		}
		return map;
	}
	
	public static HashMap getInventorySizeCalculations(IInventory inventory) {
		ItemStack stack;
		int totalSpace = 0;
		int itemCount = 0;
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			stack = inventory.getStackInSlot(i);
			if (stack == null) {
				totalSpace += 64;
			}else {
				totalSpace += stack.getMaxStackSize();
				itemCount += stack.stackSize;
			}
		}
		
		HashMap response = new HashMap();
		response.put("TotalSpace", totalSpace);
		response.put("ItemCount", itemCount);
		if (totalSpace > 0) {
			double percent = (double)100 / totalSpace * itemCount;
			percent = Math.max(Math.min(percent, 100), 0);
			response.put("InventoryPercentFull", percent);			
		}
		
		return response;
	}

	public static String getNameForItemStack(ItemStack is) {
		String name = "Unknown";
		try {
			name = is.getDisplayName();
		} catch (Exception e) {
		}
		return name;
	}

	public static String getRawNameForStack(ItemStack is) {

		String rawName = "unknown";

		try {
			rawName = is.getDisplayName().toLowerCase();
		} catch (Exception e) {
		}
		try {
			if (rawName.length() - rawName.replaceAll("\\.", "").length() == 0) {
				String packageName = is.getItem().getClass().getName()
						.toLowerCase();
				String[] packageLevels = packageName.split("\\.");
				if (!rawName.startsWith(packageLevels[0])
						&& packageLevels.length > 1) {
					rawName = packageLevels[0] + "." + rawName;
				}
			}
		} catch (Exception e) {

		}

		return rawName.trim();
	}

	public static ItemStack getStackInSlot(World world, HashMap targets, String targetName, int slot) {
		if (targets.containsKey(targetName)) {
			Object target = targets.get(targetName);
			if (target instanceof IInventory) {
				IInventory inventory = (IInventory) target;
				if (slot < inventory.getSizeInventory()) {
					return inventory.getStackInSlot(slot);
				}
			}
		}
		return null;
	}
	
	public static HashMap getMapData(World world, HashMap targets, String targetName,
			int slot) {
		ItemStack stack = getStackInSlot(world, targets, targetName, slot);
					
		if (stack != null) {
			Item item = stack.getItem();
			if (item != null && item instanceof ItemMap)
			{
				// Create a new map
				MapData data = ((ItemMap)item).getMapData(stack, world);

				// prepare the return data
				HashMap ret = new HashMap();
				ret.put("MapName", data.mapName);
				ret.put("Scale", (int)data.scale);
				ret.put("CenterX", data.xCenter);
				ret.put("CenterZ", data.zCenter);
				HashMap colors = new HashMap();
				// put all the colours in
				for (int b = 0; b < data.colors.length; b++)
				{
					colors.put(b + 1,  mapColors[data.colors[b] / 4]);
				}
				ret.put("Colors", colors);
				return ret;
			}
		}
		return null;
	}
}
