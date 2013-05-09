package openccsensors.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public interface IItemMeta {
	public int getId();
    public boolean displayInCreative();
    public Icon getIcon();
    public String getName();
	ItemStack newItemStack(int size);
}
