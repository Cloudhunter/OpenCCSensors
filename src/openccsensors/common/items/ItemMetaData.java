package openccsensors.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import openccsensors.OpenCCSensors;

public class ItemMetaData {
	private int id;
	private String name;
	private Object[] recipe;
	
	public ItemMetaData(int id, String name, Object... recipe) {
		this.id = id;
		this.name = name;
		this.recipe = recipe;
	}

	public String getName() {
		return this.name;
	}

	public int getId() {
		return this.id;
	}
	
	public void initRecipes() {
		GameRegistry.addRecipe(
				new ItemStack(OpenCCSensors.Items.genericItem, 1, this.getId()),
				this.recipe);
	}
}
