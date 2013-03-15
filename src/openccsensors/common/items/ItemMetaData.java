package openccsensors.common.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import openccsensors.OpenCCSensors;

public class ItemMetaData {
	
	private int id;
	private String name;
	
	private Icon icon;
	private String iconName;
	
	public ItemMetaData(int id, String name, String iconName) {
		this.id = id;
		this.name = name;
		this.iconName = iconName;
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

	public void registerIcon(IconRegister iconRegister) {
		icon = iconRegister.func_94245_a(iconName);
	}

	public Icon getIcon() {
		return icon;
	}
}
