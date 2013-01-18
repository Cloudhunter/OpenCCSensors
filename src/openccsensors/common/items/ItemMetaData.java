package openccsensors.common.items;

import net.minecraft.item.ItemStack;
import openccsensors.OpenCCSensors;

public class ItemMetaData {
	
	private int id;
	private String name;
	
	public ItemMetaData(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getId() {
		return this.id;
	}
	
	public ItemStack newItemStack(int count)
	{
		return new ItemStack(OpenCCSensors.Items.genericItem, count, this.id);
	}
}
