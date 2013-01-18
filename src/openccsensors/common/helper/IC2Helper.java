package openccsensors.common.helper;

import net.minecraft.item.ItemStack;

public class IC2Helper {
	public static ItemStack getItemStack(String itemName) {
		ItemStack itemStack = null;
		try {
			itemStack = ic2.api.Items.getItem(itemName);
		}catch(Exception e) {}
		return itemStack;
	}
}
