package openccsensors.common.helper;

import openccsensors.OpenCCSensors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeHelper {
	public void init() {
	}
	
	public static void addCardRecipe(int id, Object uniqueItem) {
		
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
				new ItemStack(OpenCCSensors.Items.sensorCard, 1, id),
				new Object[] {
					"rpr",
					"rrr",
					"aaa",
					Character.valueOf('r'), new ItemStack(Item.redstone),
					Character.valueOf('a'), new ItemStack(Item.paper),
					Character.valueOf('p'), uniqueItem				
				}
			));
	}
}
