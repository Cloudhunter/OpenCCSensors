package forestry.api.core;

import net.minecraft.item.ItemStack;

public interface IOreDictionaryHandler {
	void onOreRegistration(String name, ItemStack ore);
}
