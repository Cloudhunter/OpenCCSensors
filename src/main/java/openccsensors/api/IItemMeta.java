package openccsensors.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public interface IItemMeta {
	public int getId();
    public boolean displayInCreative();
    public IIcon getIcon();
    public String getName();
	ItemStack newItemStack(int size);
}
